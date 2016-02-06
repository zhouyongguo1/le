package le.oa.core;

import ninja.FilterWith;

@FilterWith({UserFilter.class, TeamFilter.class})
public class BaseTeamController extends BaseController {

}
