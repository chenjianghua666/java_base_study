# String StringBuffer StringBuilder 区别

1. String 是不可变的
2. StringBuffer append 方法有加锁, 线程安全
> 示例代码
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
### String
1. 不可变性, 线程安全,常用语参数,底层实现采用的final char[],因此是不可以变的.
2. string pool,通过字面量创建的对象会存入string pool缓存池中,所以这种方式创建的对象可以使用==进行比较
3. intern()方法, 会先将调用的值存入pool中然后返回新的引用地址,如果赋值给其他String对象使其引用同一个对象
> 代码
```java
        String strValue1 = "haha"; // 创建的对象放到string pool中
        String strValue2 = strValue1; // 引用的是同一个地址
        String strValue3 = new String("haha"); // 会创建一个新的对象
        String strValue4 = strValue3.intern(); // 引用同一个地址
        System.out.println("====>" + (strValue1 == strValue2)+ "===>"  + (strValue1 == strValue3)); // true ===> false
        // intern 先将指向的对象存入pool中然后再返回这个对象的引用, strValue3.intern()调用的pool引用的对象与strValue4指向的是同一个引用
        System.out.println(strValue3.intern() == strValue4);
        System.out.println(strValue4.intern());
```

# Integer "=="使用

> 示例代码
```java
        Integer num1 = 100;
        Integer num2 = 100;
        
        Integer num3 = 200;
        Integer num4 = 200;

        System.out.println(num1 == num2); // true
        System.out.println(num3 == num4); // false
```

之所以这样是因为再Integer类中会有一个静态缓存内部类, 该静态缓存内部类缓存了-128~128之间的数字,
那么当在字面量赋值操作的时候,会调用valueOf方式从缓存中先进行取数,-128~128之间的值必然取到的就是
同样的一个引用地址,如果大于这个区间那么会重新new一个新的对象,所以会产生上面两种不同的结果
```java
    private static class IntegerCache {
        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            // high value may be configured by property
            int h = 127;
            String integerCacheHighPropValue =
                sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    int i = parseInt(integerCacheHighPropValue);
                    i = Math.max(i, 127);
                    // Maximum array size is Integer.MAX_VALUE
                    h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
                } catch( NumberFormatException nfe) {
                    // If the property cannot be parsed into an int, ignore it.
                }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for(int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);

            // range [-128, 127] must be interned (JLS7 5.1.7)
            assert IntegerCache.high >= 127;
        }

        private IntegerCache() {}
    }
```
valueOf取值逻辑
```java
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
```
## 基本数据类型
每一个基本的数据类型都对应了一个包装类型
```java
//        int ;  Integer;
//        float; Float;
//        double; Double;
//        long; Long;
//        char; String
//        short;
//        byte;
//        boolean;
```
每一个基本的数据类型都有其对应的缓存池
1. boolean values true and false all byte values
2. short values between -128 and 127
3. int values between -128 and 127
4. char in the range \u0000 to \u007F