package pers.goetboy.common.bean;


/**
 * Lang Integer 类型转换
 *
 * @author:goetboy;
 * @date 2018 /12 /27
 **/
public class LongIntegerConverter extends AbstractTypeConverter {
    public LongIntegerConverter(Class<?> sourceClass, Class<?> targetClass) {
        super(sourceClass, targetClass);
    }

    /**
     * @param actualSourceClass 只能是 {@link Long} or {@link Integer} 类型
     * @param actualTargetClass 只能是 {@link Long} or {@link Integer} 类型
     * @param value             {@link Long} or {@link Integer} 类型
     * @return {@link Long} or {@link Integer} 类型
     */
    @Override
    public Object convert(Class<?> actualSourceClass, Class<?> actualTargetClass, Object value) {
        if (value == null) {
            return null;
        } else {
            return actualTargetClass.equals(Long.class) ? (long) (Integer) value : ((Long) value).intValue();
        }
    }
}
