package br.com.rfreforged.ReforgedGCP.model;

public enum ItemTipo {
    None(0),
    UppeItem(1),
    LowerItem(2),
    GauntletItem(3),
    ShoeItem(4),
    HelmetItem(5),
    WeaponItem(6),
    ShieldItem(7),
    CloakItem(8),
    RingItem(9),
    AmuletItem(10),
    AmmoItem(11),
    MakeToolItem(12),
    PotionItem(13),
    BagItem(14),
    BatteryItem(15),
    OreItem(16),
    ResourceItem(17),
    ForceItem(18),
    UnitKeyItem(19),
    BootyItem(20),
    MapItem(21),
    ScrollsItem(22),
    BattleDungeonItem(23),
    AnimusItem(24),
    TowerItem(25),
    TrapItem(26),
    SiegeKitItem(27),
    TicketItem(28),
    EventItem(29),
    RecoveryItem(30),
    BoxItem(31),
    FireCracker(32),
    Unmannedminer(33),
    RadarItem(34),
    PagersItem(35),
    CouponItem(36),
    UnitHead(37),
    UnitUpper(38),
    UnitLower(39),
    UnitArms(40),
    UnitShoulder(41),
    UnitBack(42),
    UnitBullet(43);

    private final int code;

    ItemTipo(int i) {
        this.code = i;
    }

    public int getCodeItem() {
        return code;
    }
}


