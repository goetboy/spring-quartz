package pers.goetboy.common.bean;

/**
 * 类型转换器接口
 *
 * @author:goetboy;
 * @date 2018 /12 /27
 **/
public interface TypeConverter {

    /**
     * 获取源类型class
     *
     * @return
     */
    Class<?> getSourceTypeClass();

    /**
     * 获取目标类型class
     *
     * @return
     */
    Class<?> getTargetTypeClass();

    /**
     * 转换操作
     * 会有接口和实现类的关系，所以必须传入实际操作的类，不然枚举等类型会得不到具体的枚举值
     *
     * @param actualSourceClass the actual source class
     * @param actualTargetClass the actual target class
     * @param value             The input value to be converted
     * @return The converted value
     */
    Object convert(Class<?> actualSourceClass, Class<?> actualTargetClass, Object value);

}