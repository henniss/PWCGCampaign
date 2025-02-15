package pwcg.campaign.plane;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.resupply.InitialSquadronEquipper;
import pwcg.campaign.resupply.depot.EquipmentWeightCalculator;
import pwcg.campaign.squadmember.SerialNumber;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.exception.PWCGException;
import pwcg.testutils.TestCampaignFactoryBuilder;
import pwcg.testutils.SquadronTestProfile;

@ExtendWith(MockitoExtension.class)
public class InitialSquadronEquipperTest
{
    public InitialSquadronEquipperTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);      
    }

    @Test
    public void testEquipSquadronGermanFighter() throws PWCGException
    {
        Campaign campaign = TestCampaignFactoryBuilder.makeCampaign(this.getClass().getCanonicalName(), SquadronTestProfile.JG_51_PROFILE_MOSCOW);        
        Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(SquadronTestProfile.JG_51_PROFILE_MOSCOW.getSquadronId());
        
        EquipmentWeightCalculator equipmentWeightCalculator = new EquipmentWeightCalculator(campaign.getDate());
        InitialSquadronEquipper squadronEquipper = new InitialSquadronEquipper(campaign, squadron, equipmentWeightCalculator);
        Equipment equipment = squadronEquipper.generateEquipment();
        
        assert(equipment.getActiveEquippedPlanes().size() == Squadron.SQUADRON_EQUIPMENT_SIZE);
        for (EquippedPlane equippedPlane : equipment.getActiveEquippedPlanes().values())
        {
            assert(equippedPlane.getArchType().equals("bf109"));
            assert(equippedPlane.getType().equals("bf109f2") || equippedPlane.getType().equals("bf109f4"));
            assert(equippedPlane.getSerialNumber() > SerialNumber.PLANE_STARTING_SERIAL_NUMBER);
        }
    }

    @Test
    public void testEquipSquadronGermanBomber() throws PWCGException
    {
        Campaign campaign = TestCampaignFactoryBuilder.makeCampaign(this.getClass().getCanonicalName(), SquadronTestProfile.KG53_PROFILE);        
        Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(SquadronTestProfile.KG53_PROFILE.getSquadronId());
        
        EquipmentWeightCalculator equipmentWeightCalculator = new EquipmentWeightCalculator(campaign.getDate());
        InitialSquadronEquipper squadronEquipper = new InitialSquadronEquipper(campaign, squadron, equipmentWeightCalculator);
        Equipment equipment = squadronEquipper.generateEquipment();
        
        assert(equipment.getActiveEquippedPlanes().size() == Squadron.SQUADRON_EQUIPMENT_SIZE);
        for (EquippedPlane equippedPlane : equipment.getActiveEquippedPlanes().values())
        {
            assert(equippedPlane.getArchType().equals("he111"));
            assert(equippedPlane.getType().equals("he111h6") || equippedPlane.getType().equals("he111h16"));
            assert(equippedPlane.getSerialNumber() > SerialNumber.PLANE_STARTING_SERIAL_NUMBER);
        }
    }

    @Test
    public void testEquipSquadronGermanDiveBomber() throws PWCGException
    {
        Campaign campaign = TestCampaignFactoryBuilder.makeCampaign(this.getClass().getCanonicalName(), SquadronTestProfile.STG77_PROFILE);        
        Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(SquadronTestProfile.STG77_PROFILE.getSquadronId());
        
        EquipmentWeightCalculator equipmentWeightCalculator = new EquipmentWeightCalculator(campaign.getDate());
        InitialSquadronEquipper squadronEquipper = new InitialSquadronEquipper(campaign, squadron, equipmentWeightCalculator);
        Equipment equipment = squadronEquipper.generateEquipment();
        
        assert(equipment.getActiveEquippedPlanes().size() == Squadron.SQUADRON_EQUIPMENT_SIZE);
        for (EquippedPlane equippedPlane : equipment.getActiveEquippedPlanes().values())
        {
            assert(equippedPlane.getArchType().equals("ju87"));
            assert(equippedPlane.getType().equals("ju87d3"));
            assert(equippedPlane.getSerialNumber() > SerialNumber.PLANE_STARTING_SERIAL_NUMBER);
        }
    }

    @Test
    public void testEquipSquadronGermanTransport() throws PWCGException
    {
        Campaign campaign = TestCampaignFactoryBuilder.makeCampaign(this.getClass().getCanonicalName(), SquadronTestProfile.TG2_PROFILE);        
        Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(SquadronTestProfile.TG2_PROFILE.getSquadronId());
        
        EquipmentWeightCalculator equipmentWeightCalculator = new EquipmentWeightCalculator(campaign.getDate());
        InitialSquadronEquipper squadronEquipper = new InitialSquadronEquipper(campaign, squadron, equipmentWeightCalculator);
        Equipment equipment = squadronEquipper.generateEquipment();
        
        assert(equipment.getActiveEquippedPlanes().size() == Squadron.SQUADRON_EQUIPMENT_SIZE);
        for (EquippedPlane equippedPlane : equipment.getActiveEquippedPlanes().values())
        {
            assert(equippedPlane.getArchType().equals("ju52"));
            assert(equippedPlane.getType().equals("ju523mg4e"));
            assert(equippedPlane.getSerialNumber() > SerialNumber.PLANE_STARTING_SERIAL_NUMBER);
        }
    }

    @Test
    public void testEquipSquadronRussianAttack() throws PWCGException
    {
        Campaign campaign = TestCampaignFactoryBuilder.makeCampaign(this.getClass().getCanonicalName(), SquadronTestProfile.REGIMENT_503_PROFILE);        
        Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(SquadronTestProfile.REGIMENT_503_PROFILE.getSquadronId());
        
        EquipmentWeightCalculator equipmentWeightCalculator = new EquipmentWeightCalculator(campaign.getDate());
        InitialSquadronEquipper squadronEquipper = new InitialSquadronEquipper(campaign, squadron, equipmentWeightCalculator);
        Equipment equipment = squadronEquipper.generateEquipment();
        
        assert(equipment.getActiveEquippedPlanes().size() == Squadron.SQUADRON_EQUIPMENT_SIZE);
        for (EquippedPlane equippedPlane : equipment.getActiveEquippedPlanes().values())
        {
            assert(equippedPlane.getArchType().equals("il2"));
            assert(equippedPlane.getType().equals("il2m41") || equippedPlane.getType().equals("il2m42"));
            assert(equippedPlane.getSerialNumber() > SerialNumber.PLANE_STARTING_SERIAL_NUMBER);
        }
    }
}
