package managers;

import actors.*;
import java.util.*;

public class BattleManager {
    public ArrayList<Entity> playerParty;
    public ArrayList<Entity> enemyParty;
    public ArrayList<Entity> combined;

    public BattleManager(ArrayList<Entity> playerParty, ArrayList<Entity>enemyParty) {
        this.playerParty = playerParty;
        this.enemyParty = enemyParty;
        combined = new ArrayList<Entity>();

        buildQueue();
    }

    // Build the turn queue by dexterity (highest dexterity moves first)
    private void buildQueue() {
        ArrayList<Entity> rawCombined = new ArrayList<Entity>();
        combined.addAll(playerParty);
        combined.addAll(enemyParty);

        Entity maxEntity = combined.get(0);

        while (combined.size() < rawCombined.size()) {
            for (int i = 0; i < rawCombined.size(); i++) {
                if (maxEntity.getDexterity() < rawCombined.get(i).getDexterity()) {
                    maxEntity = rawCombined.get(i);
                }
            }

            combined.add(maxEntity);
            rawCombined.remove(maxEntity);
        }
    }

    private void battle() {
        int victor = -1;

        while (victor < 0) {
            for (int i = 0; i < combined.size(); i++) {
                processTurn(combined.get(i));
                victor = checkBattleState();
            }
        }

        if (victor == 0) {
            // Lost
        }
        else {
            // Won
        }
    }

    private int checkBattleState() {
        if (playerParty.isEmpty()) { return 0; }
        if (enemyParty.isEmpty()) { return 1; }
        return -1;
    }

    private void processTurn(Entity entity) {
        if (entity.getEntityType() == EntityType.ENEMY) {
            // CPU control
        }
        else {
            // Player control
        }
    }

    private void attack(Entity attacker, Entity recipient) {
        recipient.damage(attacker.getWeapon().getAttackPower() - (recipient.getDefense()));
    }
}
