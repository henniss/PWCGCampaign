package pwcg.mission.flight.factory;

import pwcg.campaign.Campaign;
import pwcg.campaign.plane.PwcgRole;
import pwcg.campaign.plane.PwcgRoleCategory;
import pwcg.campaign.skirmish.Skirmish;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.PWCGLogger;
import pwcg.core.utils.PWCGLogger.LogLevel;
import pwcg.mission.flight.FlightTypes;

public class SkirmishFlightTypeFactory implements IFlightTypeFactory
{
    private Campaign campaign;
    private Skirmish skirmish;
    private IFlightTypeFactory backupFlightTypeFactory;

    public SkirmishFlightTypeFactory (Campaign campaign, Skirmish skirmish, IFlightTypeFactory backupFlightTypeFactory) 
    {
        this.campaign = campaign;
        this.skirmish = skirmish;
        this.backupFlightTypeFactory = backupFlightTypeFactory;
    }

    @Override
    public FlightTypes getFlightType(Squadron squadron, boolean isPlayerFlight, PwcgRole missionRole) throws PWCGException
    {
        missionRole = convertUnaccaptableRolesForSkirmish(missionRole, squadron);
        missionRole = skirmish.forceRoleConversion(missionRole, squadron.determineSide());
        
        FlightTypes flightType = FlightTypes.ANY;
        if (skirmish.hasFlightTypeForRole(squadron, missionRole))
        {
            flightType = skirmish.getFlighTypeForRole(squadron, missionRole);
            PWCGLogger.log(LogLevel.DEBUG, "Skirmish flight type factory returned: " + flightType + " for role " + missionRole + " Squadron " + squadron.determineDisplayName(campaign.getDate()));
        }
        else
        {
            flightType = backupFlightTypeFactory.getFlightType(squadron, isPlayerFlight, missionRole);
            PWCGLogger.log(LogLevel.DEBUG, "Backup flight type factory returned: " + flightType + " for role " + missionRole + " Squadron " + squadron.determineDisplayName(campaign.getDate()));
        }

        return flightType;
    }
    
    private PwcgRole convertUnaccaptableRolesForSkirmish (PwcgRole missionRole, Squadron squadron) throws PWCGException
    {
        if (missionRole == PwcgRole.ROLE_STRATEGIC_INTERCEPT)
        {
            return PwcgRole.ROLE_FIGHTER;
        }
        
        if (missionRole == PwcgRole.ROLE_STRAT_BOMB)
        {
            return PwcgRole.ROLE_BOMB;
        }
        
        if (missionRole == PwcgRole.ROLE_RECON)
        {
            PwcgRoleCategory squadronPrimaryRole = squadron.determineSquadronPrimaryRoleCategory(campaign.getDate());
            if (squadronPrimaryRole == PwcgRoleCategory.FIGHTER)
            {
                return PwcgRole.ROLE_FIGHTER;
            }
            else if (squadronPrimaryRole == PwcgRoleCategory.BOMBER)
            {
                return PwcgRole.ROLE_BOMB;
            }
            else if (squadronPrimaryRole == PwcgRoleCategory.RECON)
            {
                return PwcgRole.ROLE_BOMB;
            }
        }
        
        return missionRole;
    }
}
