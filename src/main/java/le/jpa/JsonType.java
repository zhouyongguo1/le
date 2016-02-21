package le.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import le.util.ObjectMapperBuilder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class JsonType implements UserType, ParameterizedType, Serializable {

    private static final long serialVersionUID = 1L;

    private static final ObjectMapper MAPPER = ObjectMapperBuilder.create();

    private static final String CLASS = "class";

    private static final String TYPE = "type";

    private Class<?> clazz;

    private int sqlType = Types.LONGNVARCHAR;

    @Override
    public void setParameterValues(Properties params) {
        String className = params.getProperty(CLASS);
        try {
            this.clazz = ReflectHelper.classForName(className, this.getClass());
        } catch (ClassNotFoundException ex) {
            throw new HibernateException(ex);
        }
        String type = params.getProperty(TYPE);
        if (type != null) {
            this.sqlType = Integer.decode(type);
        }

    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        try {
            return MAPPER.readValue(MAPPER.writeValueAsString(value), this.clazz);
        } catch (IOException e) {
            throw new HibernateException("unable to deep copy object", e);
        }
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (IOException e) {
            throw new HibernateException("unable to disassemble object", e);
        }
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equal(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        Object obj = null;
        if (!rs.wasNull()) {
            if (this.sqlType == Types.CLOB || this.sqlType == Types.BLOB) {
                byte[] bytes = rs.getBytes(names[0]);
                if (bytes != null) {
                    try {
                        obj = MAPPER.readValue(bytes, this.clazz);
                    } catch (IOException e) {
                        throw new HibernateException("unable to read object from result set", e);
                    }
                }
            } else {
                try {
                    obj = MAPPER.readValue(rs.getString(names[0]), this.clazz);
                } catch (IOException e) {
                    throw new HibernateException("unable to read object from result set", e);
                }
            }
        }
        return obj;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, this.sqlType);
        } else {

            if (this.sqlType == Types.CLOB || this.sqlType == Types.BLOB) {
                try {
                    st.setBytes(index, MAPPER.writeValueAsBytes(value));
                } catch (IOException e) {
                    throw new HibernateException("unable to set object to result set", e);
                }
            } else {
                try {
                    st.setString(index, MAPPER.writeValueAsString(value));
                } catch (IOException e) {
                    throw new HibernateException("unable to set object to result set", e);
                }
            }
        }
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }

    @Override
    public Class<?> returnedClass() {
        return this.clazz;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{sqlType};
    }
}
