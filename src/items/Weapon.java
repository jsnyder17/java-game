package items;

import java.util.StringTokenizer;

public class Weapon extends Item {
    private int attackPower;

    public Weapon() {
        super();
    }
    public Weapon(String data) {
        super();

        StringTokenizer st = new StringTokenizer(data, "|");

        name = st.nextToken();
        description = st.nextToken();
        attackPower = Integer.parseInt(st.nextToken());
    }
}
