package pwcg.aar.outofmission.phase1.elapsedtime;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.aar.data.AARPersonnelLosses;
import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.personnel.SquadronMemberFilter;
import pwcg.campaign.squadmember.SquadronMember;
import pwcg.campaign.squadmember.SquadronMembers;
import pwcg.core.exception.PWCGException;
import pwcg.testutils.TestCampaignFactoryBuilder;
import pwcg.testutils.SquadronTestProfile;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonnelOutOfMissionAiStatusHandlerTest
{
    private Campaign campaign;
    
    @BeforeAll
    public void setupSuite() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.FC);
        campaign = TestCampaignFactoryBuilder.makeCampaign(this.getClass().getCanonicalName(), SquadronTestProfile.ESC_103_PROFILE);
    }

    @Test
    public void testPersonnelLossesOutOfMission () throws PWCGException
    {     
        PersonnelOutOfMissionStatusHandler personnelLossOutOfMissionHandler = new PersonnelOutOfMissionStatusHandler();
        Map<Integer, SquadronMember> campaignMembers = campaign.getPersonnelManager().getActiveCampaignMembers();
        SquadronMembers squadronMembersInMissionOtherThanPlayer = SquadronMemberFilter.filterActiveAI(campaignMembers, campaign.getDate());
        personnelLossOutOfMissionHandler.determineFateOfShotDownPilots(squadronMembersInMissionOtherThanPlayer.getSquadronMemberCollection());
        
        Map<Integer, SquadronMember> aiKilled = new HashMap<>();
        Map<Integer, SquadronMember> aiMaimed = new HashMap<>();
        Map<Integer, SquadronMember> aiCaptured = new HashMap<>();

        for (int i = 0; i < 10; ++i)
        {
            AARPersonnelLosses outOfMissionPersonnelLosses = personnelLossOutOfMissionHandler.determineFateOfShotDownPilots(campaignMembers);
            aiKilled.putAll(outOfMissionPersonnelLosses.getPersonnelKilled());
            aiMaimed.putAll(outOfMissionPersonnelLosses.getPersonnelMaimed());
            aiCaptured.putAll(outOfMissionPersonnelLosses.getPersonnelCaptured());
        }
        
        Assertions.assertTrue (aiKilled.size() > 0);
        Assertions.assertTrue (aiMaimed.size() > 0);
        Assertions.assertTrue (aiCaptured.size() > 0);
    }

}
