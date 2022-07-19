# String StringBuffer StringBuilder 区别

1. String 是不可变的
2. StringBuffer append 方法有加锁, 线程安全

```java
 @Override
    public synchronized StringBuffer append(String str) {
        toStringCache = null;
        super.append(str);
        return this;
    }
```
3. StringBuilder append方法是非线程安全的
```java
    public StringBuilder append(String str) {
        super.append(str);
        return this;
    }
```
