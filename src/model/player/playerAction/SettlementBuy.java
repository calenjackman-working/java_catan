package model.player.playerAction;

import java.util.ArrayList;
import java.util.List;

import exceptions.IllegalActionException;
import exceptions.IllegalSettlementAdditionException;
import model.board.Board;
import model.board.CatanNode;
import model.board.pieces.ownable.Road;
import model.board.pieces.ownable.Settlement;

public class SettlementBuy implements ActionType {
    private Settlement settlement;

    public SettlementBuy(Settlement settlement) {
        this.settlement = settlement;
    }

    @Override
    public void takeAction(Board board) throws IllegalActionException {
        if (validAction(board)) {
            this.settlement.getNode().setSettlement(this.settlement);
        }
    }

    @Override
    public Boolean validAction(Board board) throws IllegalActionException {
        List<ValidityCheckRunner> validityChecks = new ArrayList<>();
        validityChecks.add(new ValidityCheckRunner() {
            @Override
            public Boolean runCheck() throws IllegalSettlementAdditionException {
                return nodeIsVacant();
            }
        });
        validityChecks.add(new ValidityCheckRunner() {
            @Override
            public Boolean runCheck() throws IllegalSettlementAdditionException {
                return adjacentNodesAreVacant();
            }
        });
        validityChecks.add(new ValidityCheckRunner() {
            @Override
            public Boolean runCheck() throws IllegalSettlementAdditionException {
                return nodeConnectsToAtLeastOneOwnedRoad();
            }
        });

        for (ValidityCheckRunner validityCheck : validityChecks) {
            if (!(validityCheck.runCheck())) {
                return false;
            }
        }
        return true;
    }

    private Boolean nodeIsVacant() throws IllegalSettlementAdditionException {
        if (!(this.settlement.getNode().getSettlement() == null)) {
            throw new IllegalSettlementAdditionException("Node is not vacant");
        }
        return true;
    }

    private Boolean adjacentNodesAreVacant() throws IllegalSettlementAdditionException {
        List<CatanNode> nodes = this.settlement.getNode().getAdjacentNodes();
        for (CatanNode node : nodes) {
            if (!(node.getSettlement() == null)) {
                throw new IllegalSettlementAdditionException("Adjacent nodes are not vacant");
            }
        }
        return true;
    }

    private Boolean nodeConnectsToAtLeastOneOwnedRoad() throws IllegalSettlementAdditionException {
        CatanNode nodeInQuestion = this.settlement.getNode();
        List<CatanNode> adjacentNodes = nodeInQuestion.getAdjacentNodes();
        for (CatanNode adjacentNode : adjacentNodes) {
            List<Road> adjacentNodeRoads = adjacentNode.getRoads();
            for (Road road : adjacentNodeRoads) {
                if ((road.getNodes().contains(nodeInQuestion))
                        && (road.getOwner().equals(this.settlement.getOwner()))) {
                    return true;
                }
            }
        }
        throw new IllegalSettlementAdditionException(
                "Proposed node is not connected to a road owned by " + this.settlement.getOwner().getPlayerName());
    }
}
