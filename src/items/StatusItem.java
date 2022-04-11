package items;

import java.util.StringTokenizer;

public class StatusItem extends Item {
    private StatusItemType statusItemType;
    private int effectAmt;

    public StatusItem() {
        super();

        statusItemType = StatusItemType.HP;
        effectAmt = 0;
    }
    public StatusItem(String data) {
        super();

        StringTokenizer st = new StringTokenizer(data, "|");

        name = st.nextToken();
        description = st.nextToken();
        //quantity = Integer.parseInt(st.nextToken());
        statusItemType = StatusItemType.valueOf(st.nextToken());
        effectAmt = Integer.parseInt(st.nextToken());
    }

    public int getEffectAmt() { return effectAmt; }

    public void setStatusItemType(StatusItemType statusItemType) { this.statusItemType = statusItemType; }
    public void setEffectAmt(int effectAmt) { this.effectAmt = effectAmt; }
}
