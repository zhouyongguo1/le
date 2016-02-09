package le.oa.core;

import com.google.inject.Inject;
import ninja.FilterWith;

@FilterWith({TeamFilter.class})
public class BaseTeamController extends BaseController {
    @Inject
    protected CurrentTeamProvider currentTeamProvider;
}
