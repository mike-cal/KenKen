package mvc.controller;

public class ControllerMediator  implements Mediator{

    private ActionController action;
    private SolutionController solution;


    public void setColleague(ActionController action,SolutionController solution){
        this.action=action;
        this.solution=solution;

    }

    @Override
    public void panelChanged(PanelColleague colleague) {
        if(colleague== action){

        }
        if(colleague == solution){
            action.action();
        }
    }
}
