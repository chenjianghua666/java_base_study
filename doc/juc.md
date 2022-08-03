# 线程
1. 并发: 同一个时刻多个线程在访问同一个资源,多个线程对一个点
2. 并行: 多个任务一起执行, 之后汇总

## 创建线程的方式
1. 通过实现 runnable 去创建
2. 通过集成Thread类来创建一个线程
3. 通过实现Callable接口来创建Thread

## 多线程
### 线程不安全
多个线程对同一个共享数据进行访问而不采取同步的操作，可能会导致结果不一致。
1. 可见性--缓存不一致： 一个线程对共享变量的修改，另外一线程能够立刻看到，当执行线程1的是cpu1，执行线程2的是cpu2，当线程1执行i=10会先将初始值
加载到cpu1高速缓存中，但并没有立即写入到主存中。当线程2执行j=i,会先去主存读取值并加载cpu2的缓存当中，线程1修改了变量后线程2并没有立即看到1修改的值
2. 原子性--分时复用引起： 要么全部执行并i执行的过程不会被任何因素打断，要么就都不执行
3. 有序性--重排序引起： jvm编译优化会对指令进行重排

#### 相对的线程安全
Java中大部分的线程安全都属于这种类型如： vector hashTable collections

#### 线程安全的实现方法
1. 互斥同步：synchronized 和 reentrantLock， 悲观并发策略
   2. 非阻塞同步： 乐观
      1. 互斥同步会阻塞线程，带来唤醒引起性能问题
      2. cas（compare and swap）
   >    乐观锁需要操作和冲突检测这两个步骤具备原子性，只能靠硬件来完成。硬件支持的原子性操作最典型的是比较交换，只有当V的值等于A，才将V的值更新为B
      3. atomicInteger
   >    JUC包里面的整数原子类
      4. ABA
   >    如果一个变量A变成B又变成A,那么cas操作会误认为它从来没被修改过, juc工具包中提供了AtomicStampedReference来解决问题,通过控制变量版本来保证正确性.
   >    采用互斥同步比原子类更高效
      5. 无同步方案: 要保证线程安全,并不是一定就要进行同步,如果一个方法本就不涉及共享数据,就无需同步措施去保证正确性
         1. 栈封闭: 多个线程访问同一个方法的局部变量属于栈中线程私有不会引起安全
         2. 线程本地存储: ThreadLocal类实现线程本地存储功能
         3. 可冲入代码Reentrant: 不依赖堆上的数据和公用的系统资源,用到的状态两都又参数中传入,不调用非可重入方法
         
      > 每个Thread都又一个ThreadLocalMap对象,Thread类中就定义ThreadLocalMap成员
      ```java
   public void set(T value) {
           Thread t = Thread.currentThread();
           ThreadLocalMap map = getMap(t);
           if (map != null) {
               map.set(this, value);
           } else {
               createMap(t, value);
           }
       }
   ```
   > ThreadLocal 并不是用来解决线程并发问题,因为不存在多线程竞争, 使用后需要调用remove,要不然可能会产生内存泄露
### JMM 内存模型
1. Java内存模型规范了jvm提供了如何禁用缓存和编译优化的方法，方法包括：
   1. volatile、synchronized和final
      1. 当一个共享变量被volatile修饰时，会保证修改的值会立即更新到主存，从而其他的线程再访问的时候能够读取主存中最新的值
   2. Happens-Before
### java 中的锁
1. 乐观锁:  任务自己在使用数据时不会有别的线程修改数据,CAS
2. 悲观锁: 在使用的数据的时候认为一定又别的线程来修改数据,因此在获得数据的时候会先加锁,确保数据不会被别的线程修改,Java中,synchronized关键字和lock
3. 自旋锁: 通过自旋锁操作减少CPU切换以及恢复现场导致的消耗
4. 公平锁: 多个线程按照申请锁的顺序来获取锁
5. 非公平: 多个线程加锁时直接尝试获取所: ReentrantLock
6. 可重入锁: 同一个线程在外层方法获取锁的时候,再进入该线程的内层方法会自动获取锁: reentrantLock 和 synchronized 都是可重入锁,优点是可以避免死锁
7. 独享锁: lock,synchronized 互斥锁
8. 共享锁: 获取共享锁的线程只能读取数据,不能修改数据
9. ### synchronized
* 同时只能被一个线程获取
* 每个对象都对应有自己的一把锁,不同实例之间互不影响.但是锁对象是.class以及synchronized修饰的static方法的时候,所有对象公用同一把锁
* synchronized修饰的方法无论方法正常执行完毕还是跑出异常都会是释放锁
缺点:
* 效率低: 只有代码执行完或者异常结束才会释放锁,不能设置超时
* 不够灵活: 加锁和释放的时机单一,每个所仅有一个单一的条件
* 无法确定是否成功获取锁, 相对Lock是可以拿到状态的

### Lock
* 加锁: lock()
* 解锁: unlock
* 尝试获取锁: tryLock()
* tryLock(long, TimeUntil): 尝试获取锁并且设置超时

> Lock 可与 Condition结合进行使用解决Synchronized条件单一的问题
> 多线程竞争一个锁时, 其余未得到锁的线程只能不停的尝试获取锁, 而不能中断,高并发的情况下会导致性能下降.ReentrantLock的lockInterruptibly()方法可以优先
> 考虑响应中断.一个线程等待时间过程,它可以中断自己,然后ReentrantLock相应这个中断,不再让线程继续等待.有了这个机制,使用ReentrantLock时就不会像synchronized
> 那样产生死锁

Synchronize
1. 锁的对象不能为空, 锁的对象信息都保存再对象头里
2. 作用域不宜过大,影响程序执行的速度
3. 避免死锁
4. 尽量使用java.until.concurrent包中的各种各样的类

指令重排
正常对象构造过程:
1. 分配内存空间
2. 初始化对象
3. 将内存空间的地址赋值给对应的引用
指令重排可能会导致对象构成过程打乱顺序,再对象未初始化完毕就已经将内存地址给暴露出来了,这会导致不可预料的结果

### volatile
```java
public class VolatileTest {
    /**
     * 这个程序可能会存在 b=3, a =1的结果,线程修改A的属性后会先存在cpu告诉缓存中,对于print线程并不可见所以读到的值是1但是已经
     **/
    int a = 1;
    int b = 2;
    public void change() {
        a = 3;
        b = a;
    }

    void print() {
        System.out.println("a=" + a + "; b="+ b);
    }

    volatile int num; // 无法保证原子性
    volatile long l; // long 和 double 分为高32和低32两部分,因此普通的long和double类型肚子可能不是原子性
    volatile double d;
    AtomicInteger num2 = new AtomicInteger(0); // 原子性操作
    public void addNum() {
        synchronized (this){ // 可以使用加锁去保证原子性 或者使用 AtomicInteger
            num ++;
        }
        num2.incrementAndGet();
    }

    @Test
    public void testAddNum() throws InterruptedException {
        final VolatileTest test = new VolatileTest();
        for (int n=0; n < 1000; n++) {
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                test.addNum();
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(test.num + "\n" + test.num2);
    }

    @Test
    public void testMethod() {
        while(true) {
            final VolatileTest test = new VolatileTest();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    test.print();
                }
            }).start();
        }
    }
}
```
实现原理: 可见性基于内存屏障实现,是一个CPU指令,防止编译器优化进行指令重排
lock前缀指令: 会将当前处理器缓存行的数据协会到系统内存, 协会内存的操作会使在其他CPU里缓存了该内存地址的数据无效

### final
1. 修饰类,不可以继承
2. final类中的所有方法都隐式为final,所以在final类中给任何方法添加final关键字是没有任何意义的
> final不可继承,如果要复用已有的final类可以使用组合的方式进行封装
3. final修饰的变量在初始化后是不可以变化的
4. static final 修饰的变量在初始化的时候必须要有值,否则编译不会通过

### JUC 
1. Lock框架和Tools类
2. Collection: 并发集合类
3. Atomic: 原子类
4. Executors: 线程池
5. Tools

LockSupport
1. park(isAbsolute, time): 阻塞线程,当time为0时会无线等待,直到调用unpark方法
2. unpark函数, 释放线程,调用之前需要确保线程是否处于活跃状态

Thread.sleep() 和 Object.wait()的区别
* sleep占有锁, wait会释放占有锁
* wait不带时间,需要另一个线程使用notify进行唤醒
* wait带时间,时间到了就会自动唤醒线程
* object.wait(),需要在synchronize中执行
**sleep不会释放锁字眼, wait会释放锁资源**
lockSupport.park() 只负责阻塞当前线程,释放资源实际上事在Condition的await()方法中实现

### 线程池
1. 降低资源消耗：创建线程和销毁线程会占用系统资源
2. 提高响应速度：创建线程和销毁线程需要占用时间
3. 方便集中管理：为了防止滥用多线程，有个统一治理的地方
> 线程池不推荐使用executors去创建,推荐使用ThreadPoolExecutor方式

线程数设置规则
参考:[线程池大小](https://cloud.tencent.com/developer/article/1832455);

线程池原理
> 一个行程集合workerSet和阻塞队列workQueue.当想线程池提交一个任务的时候,线程池会将任务先放到workQueue中,workerSet中的线程会不断的从workQueue中获取线程
> 然后执行.当workerQueue中没有任何任务的时候,worker就会阻塞,直到队列中有任务了就取出来继续执行.

线程池流程
1. 判断是否小于corePoolSize,如果事,则创建一个新的工作线程来执行任务.如果都在执行任务则进入第二步
2. 判断BlockingQueue是否已经满了,如果没有满,就将线程加入BlockingQueue.否则进入到第三步
3. 创建新的线程直到线程数达到maximumPoolSize,如果创建一个新的工作线程将使当前运行的线程数量超过maximumPoolSize,则交给RejectedExecutionHandler处理任务

线程池参数
1. corePoolSize:核心线程数，当提交一个任务给线程池时，如果当前线程池的线程数小于 corePoolSize 的话会一直创建新线程执行任务，知道线程数等于 corePoolSize。如果当前线程数为 corePoolSize，继续提交的任务被保存到阻塞队列中，等待被执行；如果执行了线程池的 prestartAllCoreThreads()方法，线程池会提前创建并启动所有核心线程。
2. workQueue:用来保存等待被执行的任务的阻塞队列
   1. ArrayBlockingQueue:基于数组结构的有界阻塞队列，按 FIFO 排序任务
   2. LinkedBlockingQueue:基于链表结构的阻塞，按 FIFO 排序任务，吞吐量通常要高，在未指明容量的时候，容量默认为 Integer.MAX_VALUE。
   3. SynchronousQueue:一个不存元素的阻塞队列，每个插入操作必须等另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于 LinkedBlockingQueue
   4. PriorityBlockingQueue:具有优先级的无界阻塞队列
   5. DelayQueue：类似于 PriorityBlockingQueue，是二叉堆实现的无界优先级阻塞队列，要求元素都实现 Delayed 接口，通过执行时延从队列中提取任务，时间没到取不出来。
3. maximumPoolSize:线程池中允许的最大线程数，如果当前阻塞队列满了，向线程池继续提交任务，如果线程池当前的线程数小于 maximumPoolSize 的值，就会继续创建线程执行任务。当阻塞队列是无界队列的话，则 maximumPoolSize 不起作用，因为无法提交至核心线程池的线程会一直持续放入 workQueue。
4. keepAliveTime：非核心线程空闲时的存活时间，即当线程没有任务执行时，该线程继续存活的时间。
5. unit: keepAliveTime 的单位
6. threadFactory:创建线程的工厂，通过自定义的线程工厂可以给每个新建的线程设置一个具有识别度的线程名。默认为 DefaultThreadFactory。
7. handler：线程池和饱和策略，当阻塞队列满了，线程池中的线程数大于 maximumPoolSize 并且没有空闲的，如果继续提交任务的话，必须采取一种策略处理这些线程无法处理的任务，线程池提供了四种策
   1. AbortPolicy:直接抛出异常，默认策略；
   2. CallerRunsPolicy:用调用者所在的线程来执行任务；
   3. DiscardOldentPolicy:丢弃阻塞队列中最靠前的任务，并执行当前任务；
   4. DiscardPolicy:直接丢弃任务。 我们也可以根据实际的应用场景实现 RejectedExecutionHandler 接口，自定义饱和策略，如记录日志或持久化存储不能处理的任务。

创建线程池的方式:
* FixedThreadPool 定长线程池
* ScheduledThreadPool 定时线程池
* CacheThreadPool 可缓存线程池
* SingleThreadExecutor 单线程池: 只有一个核心线程,如果线程结束,则会创建一个新的县城任务来继续执行任务,唯一的线程可以保证所有提交的任务顺序执行,使用了LinkedBlockingQueue无界队列 
* 自定义线程池

线程池的核心实现类是ThreadPoolExecutor
> ThreadPoolExecutor实现的顶层接口是Executor，顶层接口Executor提供了一种思想：将任务提交和任务执行进行解耦。用户无需关注如何创建线程，如何调度线程来执行任务，用户只需提供Runnable对象，将任务的运行逻辑提交到执行器(Executor)中，由Executor框架完成线程的调配和任务的执行部分

线程池运行主要分成两部分: 任务管理,线程管理,任务管理部分充当生产者的角色,当任务提交后,线程池会判断该任务后续的流转
1. 直接申请线程执行任务
2. 缓冲到队列中等待线程执行
3. 拒绝该任务

ThreadPoolExecutor的运行状态有5中,分别为:
1. running: 能接受新提交的任务,并且也能处理阻塞队列中的任务
2. shutdown: 关闭状态,不再接受新提交的任务,但却可以继续处理阻塞队列中已保存的任务
3. stop: 不能接受新任务,也步处理队列中的任务,会中断正在处理任务的线程
4. tidying: 所有的任务都已终止了, workerCount为0
5. terminated: 在terminated()方法执行完后进入该状态

死锁: 两个任务相互等待彼此执行完毕

```java
public class ThreadPoolTest {
    private static final int CORE_POOL_SIZE = 5; // 核心线程数
    private static final int MAX_POOL_SIZE = 10; // 线程池最大线程数
    private static final int QUEUE_CAPACITY = 10; // 任务队列,用来存储等待执行任务的队列
    private static final Long KEEP_ALIVE_TIME = 1L;
    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadFactory() { // 自定义线程名称
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "my thread" + r.hashCode());
                    }
                },
        new ThreadPoolExecutor.CallerRunsPolicy()
        );

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("current thread name: "+Thread.currentThread().getName());
                System.out.println(executor.getActiveCount());
            });
        }
        executor.shutdown(); // 终止线程池
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finished all threads");
    }
}
```
