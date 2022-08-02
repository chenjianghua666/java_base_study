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

