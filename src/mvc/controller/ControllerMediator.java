package mvc.controller;

public class ControllerMediator  implements Mediator{

    private ActionController action;
    private SolutionController solution;
    private GridController grid;


    public void setColleague(ActionController action,SolutionController solution,GridController grid){
        this.action=action;
        this.grid=grid;
        this.solution=solution;

    }

    @Override
    public void panelChanged(PanelColleague colleague) {
        if(colleague== action){
            grid.action();
        }
        if(colleague == solution){
            action.action();
        }
    }
}
