package br.com.rfreforged.ReforgedGCP.model;

public enum Classe {

    // >> Cora <<
    /*Mage*/
    SpiritualistC("CFB0"),
    Magus("CFF1"),
    Summoner("CFF2"),
    ArchMagus("CFS1"),
    Reave("CFS2"),
    Faust("CFS3"),
    /*Ranger*/
    RangerC("CRB0"),
    Archer("CRF1"),
    Hunter("CRF2"),
    MarksMan("CRS1"),
    Sleeper("CRS2"),
    Redeemer("CRS3"),
    /*Melee*/
    WarriorC("CWB0"),
    Crusader("CWF1"),
    Knight("CWF2"),
    Zealot("CWS1"),
    Paladin("CWS2"),
    Templar("CWS3"),
    /*Specialista*/
    SpecialistC("CSB0"),
    Craftsman("CSF1"),
    Artisan("CSS1"),

    // >> Acc <<
    /* Ranger */
    RangerA("ARB0"),
    Gunner("ARF1"),
    Scout("ARF2"),
    Annihilator("ARS1"),
    Desolator("ARS2"),
    Infiltrator("ARS3"),
    /*Melee*/
    WarriorA("AWB0"),
    Destroyer("AWF1"),
    Gladius("AWF2"),
    DreadNought("AWS2"),
    Punisher("AWS1"),
    WarderA("AWS3"),
    /*Spec*/
    SpecialistA("ASB0"),
    Engineer("ASF1"),
    BattleLeader("ASS2"),
    Scientist("ASS1"),

    // >> Bellato <<
    /*Melee*/
    WarriorB("BWB0"),
    Myrmidon("BWF1"),
    Berserker("BWS1"),
    WarderB("BWS2"),
    Sentry("BWF2"),
    Sentinel("BWS3"),
    /*Ranger*/
    RangerB("BRB0"),
    Vigilante("BRF1"),
    SnapShooter("BRF2"),
    Sniper("BRS1"),
    Harrier("BRS2"),
    Saboteur("BRS3"),
    /*Mage*/
    SpiritualistB("BFB0"),
    Mage("BFF1"),
    Candra("BFF2"),
    ArchMage("BFS1"),
    Chronomancer("BFS2"),
    SoulChandra("BFS3"),
    /*Specialista*/
    SpecialistB("BSB0"),
    Mechanic("BSF1"),
    Rider("BSF2"),
    Machinist("BSS1"),
    ArmorDriver("BSS2");

    private String classe;

    Classe(String cfs1) {
        this.classe = cfs1;
    }

    public String getClasse() {
        return classe;
    }
}
