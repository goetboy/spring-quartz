package pers.goetboy.common.bean;


import pers.goetboy.common.bean.enums.IEnumInterface;

/**
 * IEnum到String转换器
 * @author:goetboy;
 * @date 2018 /12 /27
 **/
public class EnumStringConverter extends AbstractTypeConverter {

    public EnumStringConverter(Class<?> sourceClass, Class<?> targetClass) {
        super(sourceClass, targetClass);
    }

    @Override
    public Object convert(Class<?> actualSourceClass, Class<?> actualTargetClass, Object value) {

        if (value == null) {
            return null;
        }
        if (IEnumInterface.class.isAssignableFrom(this.getSourceTypeClass())
                && this.getSourceTypeClass().isAssignableFrom(value.getClass())
                && this.getTargetTypeClass().equals(String.class)) {

            return ((IEnumInterface) value).getCode();
        } else if (String.class.equals(this.getSourceTypeClass()) && this.getSourceTypeClass().equals(value.getClass())
                && IEnumInterface.class.isAssignableFrom(this.getTargetTypeClass())) {

            IEnumInterface[] iEnums = (IEnumInterface[]) actualTargetClass.getEnumConstants();
            for (IEnumInterface iEnum : iEnums) {
                if (iEnum.getCode().equals(String.valueOf(value))) {
                    return iEnum;
                }
            }
        }
        return value;
    }
}