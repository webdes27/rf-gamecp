package br.com.rfreforged.ReforgedGCP.model;

public enum ItemTipo {
    UpperItem(0),
    LowerItem(1),
    GauntletItem(2),
    ShoeItem(3),
    HelmetItem(4),
    ShieldItem(5),
    WeaponItem(6),
    CloakItem(7),
    RingItem(8),
    AmuletItem(9),
    BulletItem(10),
    MakeToolItem(11),
    PotionItem(13),
    BagItem(14),
    BatteryItem(15),
    OreItem(16),
    ResourceItem(18),
    ForceItem(17),
    UnitKeyItem(19),
    BootyItem(20),
    MapItem(21),
    ScrollsItem(22),
    BattleDungeonItem(23),
    AnimusItem(24),
    GuardTowerItem(25),
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


