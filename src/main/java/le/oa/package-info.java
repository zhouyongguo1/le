@TypeDefs({
        @TypeDef(name = "localDateTimeType",
                defaultForType = LocalDateTime.class,
                typeClass = LocalDateTimeType.class),
        @TypeDef(name = "localDateType",
                defaultForType = LocalDate.class,
                typeClass = LocalDateType.class)
})

@FilterDef(
        name = "team",
        parameters = {@ParamDef(name = "teamId", type = "int")},
        defaultCondition = "team_id = :teamId"
) package le.oa; // SUPPRESS

import le.jpa.LocalDateTimeType;
import le.jpa.LocalDateType;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.time.LocalDate;
import java.time.LocalDateTime;
