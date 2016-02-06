@FilterDef(
        name = "team",
        parameters = {@ParamDef(name = "teamId", type = "int")},
        defaultCondition = "team_id = :teamId"
) package le.oa; // SUPPRESS

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
