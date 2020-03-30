package br.com.rfreforged.ReforgedGCP.model.equipamento;

public enum ItemTipo {
    UpperItem(0, "UpperItem.csv", "u"),
    LowerItem(1, "LowerItem.csv", "l"),
    GauntletItem(2, "GauntletItem.csv", "g"),
    ShoeItem(3, "ShoeItem.csv", "s"),
    HelmetItem(4, "HelmetItem.csv", "h"),
    ShieldItem(5, "ShieldItem.csv", "d"),
    WeaponItem(6, "WeaponItem.csv", "w"),
    CloakItem(7, "CloakItem.csv", "k"),
    RingItem(8, "RingItem.csv", "i"),
    AmuletItem(9, "AmmuletItem.csv", "a"),
    BulletItem(10, "BulletItem.csv", "b"),
    MakeToolItem(11, "MakeToolItem.csv", "m"),
    PotionItem(13, "PotionItem.csv", "p"),
    BagItem(14, "BagItem.csv", "e"),
    BatteryItem(15, "BatteryItem.csv", "t"),
    OreItem(16, "OreItem.csv", "iore"),
    ResourceItem(18, "ResourceItem.csv", "r"),
    ForceItem(17, "ForceItem.csv", "c"),
    UnitKeyItem(19, "UnitKeyItem.csv", "n"),
    BootyItem(20, "BootyItem.csv", "y"),
    MapItem(21, "MapItem.csv", "z"),
    ScrollsItem(22, "ScrollsItem.csv", "q"),
    BattleDungeonItem(23, "BattleDungeonItem.csv", "x"),
    AnimusItem(24, "AnimusItem.csv", "j"),
    GuardTowerItem(25, "GuardTowerItem.csv", "gt"),
    TrapItem(26, "TrapItem.csv", "tr"),
    SiegeKitItem(27, "SiegeKitItem.csv", "sk"),
    TicketItem(28, "TicketItem.csv", "tip"),
    EventItem(29, "EventItem.csv", "ev"),
    RecoveryItem(30, "RecoveryItem.csv", "re"),
    BoxItem(31, "BoxItem.csv", "bx"),
    FireCracker(32, "FireCracker.csv", "fi"),
    Unmannedminer(33, "Unmannedminer.csv", "un"),
    RadarItem(34, "RadarItem.csv", "rd"),
    NPCLinkItem(35, "NPCLinkItem.csv", "lk"),
    CouponItem(36, "CouponItem.csv", "cu");

    private final int codigo;
    private final String arquivo;
    private final String letra;

    ItemTipo(int i, String arquivo, String letra) {
        this.codigo = i;
        this.letra = letra;
        this.arquivo = arquivo;
    }

    public int getCodeItem() {
        return codigo;
    }

    public String getArquivo() {
        return arquivo;
    }

    public String getLetra() {
        return letra;
    }
}


