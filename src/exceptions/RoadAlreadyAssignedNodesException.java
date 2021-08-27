package exceptions;

public class RoadAlreadyAssignedNodesException extends Exception {
    public RoadAlreadyAssignedNodesException() {
        super();
    }

    public RoadAlreadyAssignedNodesException(String msg) {
        super(msg);
    }
}
