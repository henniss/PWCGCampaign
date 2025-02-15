package integration.campaign.io.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.campaign.SquadHistory;
import pwcg.campaign.SquadHistoryEntry;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.io.json.SquadronIOJson;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.DateUtils;

@ExtendWith(MockitoExtension.class)
public class SquadronHistoryValidationTest
{
    @Test
    public void readJsonBoSSquadronsTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        List<Squadron> squadrons = SquadronIOJson.readJson();
        Assertions.assertTrue (squadrons.size() > 0);
        
        for (Squadron squadron : squadrons)
        {
            verifyVVSTransition(squadron);
            verifyBoSTransitionDates(squadron);
        }        
    }
    
    private void verifyVVSTransition(Squadron squadron) throws PWCGException
    {
        if (squadron.getSquadronId() == 10131136)
        {
            SquadHistory squadronHistory = squadron.getSquadHistory();
            Assertions.assertTrue (squadronHistory != null);
            
            SquadHistoryEntry  squadHistoryEntry = squadronHistory.getSquadHistoryEntry(DateUtils.getDateYYYYMMDD("19420301"));
            Assertions.assertTrue (squadHistoryEntry != null);
            Assertions.assertTrue (squadHistoryEntry.getArmedServiceName().equals("Voyenno-Vozdushnye Sily"));
            Assertions.assertTrue (squadHistoryEntry.getSquadName().equals("45th Bomber Air Regiment"));
            Assertions.assertTrue (squadHistoryEntry.getSkill() == 40);
            
            assert(squadron.determineSquadronSkill(DateUtils.getDateYYYYMMDD("19420228")) == 30);
            assert(squadron.determineDisplayName(DateUtils.getDateYYYYMMDD("19420228")).equals("136th Bomber Air Regiment"));

            assert(squadron.determineSquadronSkill(DateUtils.getDateYYYYMMDD("19420301")) == 40);
            assert(squadron.determineDisplayName(DateUtils.getDateYYYYMMDD("19420301")).equals("45th Bomber Air Regiment"));
        }
    }
    
    private void verifyBoSTransitionDates(Squadron squadron) throws PWCGException
    {
        SquadHistory squadronHistory = squadron.getSquadHistory();
        if (squadronHistory != null)
        {
            validateBoSSquadHistoryEntries(squadronHistory);
        }        
    }

    private void validateBoSSquadHistoryEntries(SquadHistory squadronHistory) throws PWCGException
    {
        validateNoDuplicateEntries(squadronHistory);
        validateNoOutOfOrderEntries(squadronHistory);
    }
    
    private void validateNoDuplicateEntries(SquadHistory squadronHistory) throws PWCGException
    {
        List<SquadHistoryEntry> validatedEntries = new ArrayList<>();
        for (SquadHistoryEntry  squadHistoryEntry : squadronHistory.getSquadHistoryEntries())
        {
            for (SquadHistoryEntry validatedSquadHistoryEntry : validatedEntries)
            {
                if (squadHistoryEntry.getDate().equals(validatedSquadHistoryEntry.getDate()))
                {
                    String errorMsg = squadHistoryEntry.getSquadName() + " duplicate transition date " + squadHistoryEntry.getDate(); 
                    throw new PWCGException(errorMsg);
                }
            }
            
            validatedEntries.add(squadHistoryEntry);
        }
    }
    
    private void validateNoOutOfOrderEntries(SquadHistory squadronHistory) throws PWCGException
    {
        SquadHistoryEntry lastEntry = null;
        for (SquadHistoryEntry  squadHistoryEntry : squadronHistory.getSquadHistoryEntries())
        {
            if (lastEntry != null)
            {
                Date thisEntryDate = DateUtils.getDateYYYYMMDD(squadHistoryEntry.getDate());
                Date lastEntryDate = DateUtils.getDateYYYYMMDD(lastEntry.getDate());
                if (!thisEntryDate.after(lastEntryDate))
                {
                    String errorMsg = squadHistoryEntry.getSquadName() + " out of sequence transition date " + squadHistoryEntry.getDate(); 
                    throw new PWCGException(errorMsg);
                }
            }
            
            lastEntry = squadHistoryEntry;
        }
    }
}
