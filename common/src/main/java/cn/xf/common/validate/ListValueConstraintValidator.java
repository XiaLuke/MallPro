package cn.xf.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 列表值约束验证器
 *
 * @author XF
 * @date 2022/02/16
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private Set<Integer> set = new HashSet<>();

    /**
     * 初始化方法
     *
     * @param constraintAnnotation 约束注释
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] value = constraintAnnotation.value();
        for (int item : value) {
            set.add(item);
        }
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * 是否校验成功
     *
     * @param value   字段提交校验的值
     * @param context 上下文
     * @return boolean
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}
