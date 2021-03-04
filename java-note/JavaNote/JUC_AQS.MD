#JUC
2.1 可重入锁
2.2 LockSupport
2.3 AbstractQueuedSynchronizer之AQS
#前置知识
CAS->
    public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);

#从ReentrantLock开始解析AQS   -> AQS = state + CLH（双向队列）   Craig, Landin, Hagersten
    Lock接口的实现类，基本都是通过【聚合】了一个【队列同步器】的子类完成线程访问控制的。
  #公平锁与非公平锁
    区别：公平锁在获取同步状态时，使用hasQueuedPredecessors()方法，判断队列中是否存在有效节点，判断当前节点是否为下一个节点（先来先得）；非公平锁，（抢占式）
    默认非公平锁。
     public ReentrantLock() {
            sync = new NonfairSync();
     }
     public ReentrantLock(boolean fair) {
             sync = fair ? new FairSync() : new NonfairSync();
     }
#Lock()   
    ReentrantLock > Sync > NonfairSync/FairSync  .lock()   
# acquire()
    AbstractQueuedSynchronizer.acquire()
       public final void acquire(int arg) {
           if (!tryAcquire(arg) && //  后续线程抢不到资源时，(!tryAcquire(arg)为true
                acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
                selfInterrupt();
       }
#acquire() > tryAcquire() > nonfairTryAcquire()       
    AbstractQueuedSynchronizer.tryAcquire() > ReentrantLock.tryAcquire() > ReentrantLock.nonfairTryAcquire()
   #设计模式- >模版方法模式
        要求子类必须实现
        protected boolean tryAcquire(int arg) {
                  throw new UnsupportedOperationException();
        }
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {//没有等待线程，设置信号量，设置当前线程为持有线程
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
   #可重入锁
            else if (current == getExclusiveOwnerThread()) {
            //当前线程为持有线程，无需重复获取锁
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
#acquire() > addWaiter(Node.EXCLUSIVE), arg) > enq(node)
            private Node addWaiter(Node mode) {
            //mode > Node.EXCLUSIVE : Marker to indicate a node is waiting in exclusive mode */
                Node node = new Node(Thread.currentThread(), mode);
            // Try the fast path of enq; backup to full enq on failure
                Node pred = tail;      //AQS的尾指针是否为空
                if (pred != null) {//尾指针非空，当前线程首个入队，
                    node.prev = pred;//把当前线程的prev指针指向AQS的尾指针
                    if (compareAndSetTail(pred, node)) {//，AQS的尾指针指向当前线程
                        pred.next = node;//，原尾指针的next指针，指向当前线程
                        return node;
                    }
                }
                enq(node);
                return node;
            }
            private Node enq(final Node node) {
                for (;;) {//自旋
                    Node t = tail;
   #傀儡节点/哨兵节点， 用于占位，系统创建   那出队的时候怎么办呢？傀儡节点什么时候出队呢？->unlock()
                    if (t == null) { // Must initialize
                        if (compareAndSetHead(new Node()))
                            tail = head;
                    } else {
                        node.prev = t; //自旋，为用户线程设置prev指针
                        if (compareAndSetTail(t, node)) {
                        //尾指针指向当前线程，哨兵节点的next指针指向当前线程
                            t.next = node;
                            return t;
                        }
                    }
                }
            }
#acquire() > acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        final boolean acquireQueued(final Node node, int arg) { //arg 从acquire阶段传入，默认为1
            boolean failed = true;
            try {
                boolean interrupted = false;
   #这里的自旋需要和unlock结合看
                for (;;) {
                    final Node p = node.predecessor();//获取当前节点的prev节点（前节点）
                    //个人见解：再次尝试抢占资源
                    if (p == head && tryAcquire(arg)) {//如果当前节点的prev节点是头结点，尝试抢占资源（unlock之后能抢到  nonfairTryAcquire  方法，当前线程会成为占用资源的线程）
   #head指针指向当前节点，head节点（哨兵节点）出队                
                        setHead(node); 
                                        private void setHead(Node node) {
                                            head = node;
                                            node.thread = null; //当前线程不能在持有线程和队列同时存在
                                            node.prev = null;   //当前节点为头节点，其前节点为空
                                        }
                        p.next = null; // help GC 原哨兵节点的next指针置空，完全失去引用，最终被GC   
                        failed = false; 
                        return interrupted; //
                    }
                    //抢占失败，阻塞，直至唤醒
                    if (shouldParkAfterFailedAcquire(p, node) && //第一次返回false ，第二次返回true
                        parkAndCheckInterrupt())  //这个方法内部一直被阻塞，知道他因为某种原因被中断（如其他线程执行了unlock方法，则代表资源被释放，会进入上一步的if判断，抢占到资源，返回true）
                        interrupted = true;
                }
            } finally {
   #取消排队
                if (failed)
                    cancelAcquire(node);
            }
        }
        
        private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
            int ws = pred.waitStatus;//获取（当前线程）prev节点的状态  初始化是0
            //如果是singal  （-1），即等待被占用的资源释放，直接返回true       
            //准备继续调用parkAndCheckInterrupt()方法，挂起线程
            if (ws == Node.SIGNAL)
                return true;
            //ws>0,说明是CANCELLED状态
            if (ws > 0) {
                do {//循环判断prev节点是否也为CANCELLED状态，忽略该节点的状态，直接连接队列
                    node.prev = pred = pred.prev;
                } while (pred.waitStatus > 0);
                pred.next = node;
            } else {
            //将当前节点的prev节点设置为SINGAL状态，用户后续唤醒操作
                compareAndSetWaitStatus(pred, ws, Node.SIGNAL); //从0 到 -1，将哨兵节点的waitstatus变成-1
            }
            return false;
        }        
        
        private final boolean parkAndCheckInterrupt() {
        //线程挂起，程序不在继续执行
            LockSupport.park(this);
         //根据park方法API描述，以下三种情况会继续执行
         //1.被unpark
         //2.被中断 interrupt
         //3.其他不合逻辑的返回
         //因上述三种情况，程序执行至此，返回当前线程的终端状态，并清空中断状态
         //如果由于被中断，该方法返回true
            return Thread.interrupted();
        }
 #unlock()
    >sync.release(1)
        >tryRelease(arg)
        >unparkSuccessor
  #release(arg)               
        public final boolean release(int arg) {
            if (tryRelease(arg)) {
                Node h = head; //哨兵节点
                if (h != null && h.waitStatus != 0) //waitStatus在compareAndSetWaitStatus时变为-1
                    unparkSuccessor(h);
                return true;
            }
            return false;
        }    
   #tryRelease(arg)    
        protected final boolean tryRelease(int releases) { /release = 1
               int c = getState() - releases; //getState() = 1
               if (Thread.currentThread() != getExclusiveOwnerThread())
                   throw new IllegalMonitorStateException();
               boolean free = false;
               if (c == 0) {
                   free = true;
                   setExclusiveOwnerThread(null); //取消当前线程的占用状态
               }
               setState(c);
               return free;
         }
   #unparkSuccessor(h);
    private void unparkSuccessor(Node node) { //node是哨兵节点
        int ws = node.waitStatus;   
        if (ws < 0)
            compareAndSetWaitStatus(node, ws, 0); //重置waitStatus 为 0 
        Node s = node.next;  // s 为当前节点
        if (s == null || s.waitStatus > 0) { // 节点的waitStatus 默认为0
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
  #唤醒下一个节点    之前 parkAndCheckInterrupt方法的阻塞 解除，返回 false
            LockSupport.unpark(s.thread); // 唤醒节点
    }
        
        
        
        
        
           
