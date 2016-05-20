package le.web.freemarker;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleDate;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class ExtendedObjectWrapper extends DefaultObjectWrapper {

    @Override
    public TemplateModel wrap(Object obj) throws TemplateModelException {
        if (obj == null) {
            return super.wrap(obj);
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime utcDateTime = (LocalDateTime) obj;
            Date date = Date.from(utcDateTime.toInstant(ZoneOffset.UTC));
            return new SimpleDate(date, TemplateDateModel.DATETIME);
        } else if (obj instanceof LocalDate) {
            LocalDate localDate = (LocalDate) obj;
            Date date = Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant());
            return new SimpleDate(date, TemplateDateModel.DATE);
        }
        return super.wrap(obj);
    }
}
