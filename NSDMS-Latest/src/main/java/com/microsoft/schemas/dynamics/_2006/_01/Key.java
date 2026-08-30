
package com.microsoft.schemas.dynamics._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics.gp._2006._01.BackOfficeRoleAssignmentKey;
import com.microsoft.schemas.dynamics.gp._2006._01.BackOfficeRoleKey;
import com.microsoft.schemas.dynamics.gp._2006._01.BehaviorKey;
import com.microsoft.schemas.dynamics.gp._2006._01.BehaviorOptionKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ChangedBusinessObjectKey;
import com.microsoft.schemas.dynamics.gp._2006._01.GLTransactionLineKey;
import com.microsoft.schemas.dynamics.gp._2006._01.GreatPlainsKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ParameterKey;
import com.microsoft.schemas.dynamics.gp._2006._01.PolicyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectChangeOrderBudgetKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectChangeOrderFeeKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectChangeOrderFeeLineKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectChangeOrderFeeLineScheduleKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectChangeOrderPayCodeHourlyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectChangeOrderPayCodeSalaryKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectDepartmentKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectEmployeeExpenseDistributionKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectEmployeeExpenseLineKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectEmployeeExpenseLineTaxKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectEquipmentItemKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectEquipmentKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectFeeItemKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectFeeKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectFeeScheduleKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectMiscellaneousLogDistributionKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectMiscellaneousLogLineKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectTimesheetDistributionKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ProjectTimesheetLineKey;
import com.microsoft.schemas.dynamics.gp._2006._01.WorkersCompensationKey;


/**
 * <p>Java class for Key complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Key"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Key")
@XmlSeeAlso({
    PolicyKey.class,
    BackOfficeRoleKey.class,
    BehaviorKey.class,
    BehaviorOptionKey.class,
    ParameterKey.class,
    BackOfficeRoleAssignmentKey.class,
    GLTransactionLineKey.class,
    WorkersCompensationKey.class,
    ProjectClassKey.class,
    ProjectEmployeeExpenseLineKey.class,
    ProjectEmployeeExpenseLineTaxKey.class,
    ProjectMiscellaneousLogLineKey.class,
    ProjectTimesheetLineKey.class,
    ProjectEmployeeExpenseDistributionKey.class,
    ProjectMiscellaneousLogDistributionKey.class,
    ProjectTimesheetDistributionKey.class,
    ProjectChangeOrderBudgetKey.class,
    ProjectChangeOrderPayCodeSalaryKey.class,
    ProjectChangeOrderPayCodeHourlyKey.class,
    ProjectChangeOrderFeeKey.class,
    ProjectChangeOrderFeeLineKey.class,
    ProjectFeeKey.class,
    ProjectChangeOrderFeeLineScheduleKey.class,
    ProjectDepartmentKey.class,
    ProjectEquipmentItemKey.class,
    ProjectEquipmentKey.class,
    ProjectFeeItemKey.class,
    ProjectFeeScheduleKey.class,
    GreatPlainsKey.class,
    ChangedBusinessObjectKey.class,
    OrganizationKey.class
})
public abstract class Key {


}
