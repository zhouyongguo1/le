package le.web.binding;

import com.google.common.base.Strings;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBinder {

    private static final Pattern INDEX_NOTATION = Pattern.compile(".*\\[(\\d+)\\].*");

    public <T> T bind(Map<String, String[]> properties, Class<T> clazz) {
        T t;

        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BindingException("Failed to instantiate " + clazz.getName(), e);
        }

        try {
            instantiateNestedObjects(t, properties.keySet());
            ConvertUtilsBean convertUtilsBean = buildConvertUtilsBean();
            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());
            beanUtilsBean.populate(t, properties);
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | InstantiationException e) {
            throw new BindingException("Failed to bind properties", e);
        }

        return t;
    }

    private ConvertUtilsBean buildConvertUtilsBean() {
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean() {
            @Override
            public Object convert(String value, Class clazz) {
                if (clazz.isEnum()) {
                    return Strings.isNullOrEmpty(value) ? null : Enum.valueOf(clazz, value);
                } else {
                    return super.convert(value, clazz);
                }
            }
        };
        convertUtilsBean.register(false, true, 0);
        return convertUtilsBean;
    }

    private void instantiateNestedObjects(Object object, Set<String> propertyNames)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        String[] names = propertyNames.toArray(new String[propertyNames.size()]);
        // Reverse sort so property name with a greater index comes first
        // e.g. ["items.[0].name", "items.[1].name"] will become ["items.[1].name", "items.[0].name"]
        Arrays.sort(names, NameReverseComparator.REVERSE_ORDER);

        for (String name : names) {
            String[] paths = name.split("\\.");
            if (paths.length == 1) {
                continue;
            }

            Object nested = object;
            for (int i = 0; i < paths.length - 1; i++) {
                String path = paths[i];
                Object value = PropertyUtils.getProperty(nested, path);

                if (value != null) {
                    nested = value;
                    continue;
                }

                Class<?> propertyType;
                if (nested.getClass().isArray()) {
                    propertyType = nested.getClass().getComponentType();
                } else {
                    propertyType = PropertyUtils.getPropertyDescriptor(nested, path).getPropertyType();
                }

                if (propertyType.isArray()) {
                    Matcher matcher = INDEX_NOTATION.matcher(paths[i + 1]);
                    if (!matcher.matches()) {
                        throw new BindingException("Invalid property name '" + name + "'");
                    }
                    int arraySize = Integer.parseInt(matcher.group(1)) + 1;
                    value = Array.newInstance(propertyType.getComponentType(), arraySize);
                } else {
                    value = propertyType.newInstance();
                }

                PropertyUtils.setProperty(nested, path, value);
                nested = value;
            }
        }
    }

    static class NameReverseComparator implements Comparator<String> {
        static final NameReverseComparator REVERSE_ORDER
                = new NameReverseComparator();

        @Override
        public int compare(String name1, String name2) {
            Matcher matcher1 = INDEX_NOTATION.matcher(name1);
            if (matcher1.matches()) {
                Matcher matcher2 = INDEX_NOTATION.matcher(name2);
                if (matcher2.matches()) {
                    String[] paths1 = name1.split("\\.");
                    String[] paths2 = name2.split("\\.");

                    if (paths1.length == paths2.length) {
                        for (int i = 0; i < paths1.length; i++) {
                            matcher1 = INDEX_NOTATION.matcher(paths1[i]);
                            if (matcher1.matches()) {
                                matcher2 = INDEX_NOTATION.matcher(paths2[i]);
                                if (matcher2.matches()) {
                                    int v2 = Integer.parseInt(matcher2.group(1));
                                    int v1 = Integer.parseInt(matcher1.group(1));

                                    if (v2 > v1) {
                                        return 1;
                                    } else if (v2 < v1) {
                                        return -1;
                                    }
                                }
                            } else if (paths1[i].compareTo(paths2[i]) != 0) {
                                return paths2[i].compareTo(paths1[i]);
                            }
                        }
                    }
                }
            }

            return name2.compareTo(name1);
        }
    }
}
