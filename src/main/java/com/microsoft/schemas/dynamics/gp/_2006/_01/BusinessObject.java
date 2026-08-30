
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessObject"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Extensions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ExtensionList" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessObject", propOrder = {
    "extensions"
})
@XmlSeeAlso({
    Company.class,
    Currency.class,
    CurrencyPostingAccount.class,
    CurrencyAccess.class,
    MulticurrencySetup.class,
    BackOfficeRole.class,
    BackOfficeRoleAssignment.class,
    Batch.class,
    Bank.class,
    CountryRegionCode.class,
    PaymentCardType.class,
    PaymentTerms.class,
    Salesperson.class,
    SalesTerritory.class,
    ShippingMethod.class,
    TaxSchedule.class,
    TaxScheduleDetail.class,
    TaxDetail.class,
    DynamicsOnlineConfiguration.class,
    ServiceEquipment.class,
    GLTransaction.class,
    Applicant.class,
    ApplicantApplication.class,
    ApplicantEducation.class,
    ApplicantReference.class,
    ApplicantInterview.class,
    ApplicantTest.class,
    ApplicantWorkHistory.class,
    ApplicantSkill.class,
    Employee.class,
    EmployeePayCode.class,
    HRRequisition.class,
    Skill.class,
    SkillSet.class,
    PriceLevel.class,
    Warehouse.class,
    Pricing.class,
    UofMSchedule.class,
    ItemVendor.class,
    ItemWarehouse.class,
    ItemCurrency.class,
    Item.class,
    PlannedOrder.class,
    ProjectBudget.class,
    ProjectChangeOrder.class,
    ProjectContract.class,
    Project.class,
    Vendor.class,
    PurchaseOrder.class,
    PurchaseReceipt.class,
    PurchaseInvoice.class,
    CorporateAccount.class,
    Customer.class,
    CashReceipt.class,
    CustomerReceivablesSummary.class,
    SalesProcessHoldSetup.class,
    SalespersonCommissions.class,
    Workflow.class,
    GLAccountFormat.class,
    SalesHistoryOptions.class,
    SalesSummary.class,
    CommissionSummary.class,
    SalespersonHistory.class,
    SalesTerritoryHistory.class,
    ServiceWarrantyCode.class,
    ServiceEquipmentReading.class,
    ServiceDocument.class,
    ReturnMaterialAuthorizationLine.class,
    SalesSerialLotBase.class,
    ServiceAudit.class,
    ServiceCharge.class,
    ServiceEquipmentMeter.class,
    ServiceDistanceTraveled.class,
    ServiceLine.class,
    ServiceEquipmentCode.class,
    GLTransactionLine.class,
    GLAccount.class,
    GLVariableAllocationDistributionAccount.class,
    GLVariableAllocationBreakdownAccount.class,
    GLAccountCurrency.class,
    GLFixedAllocationDistributionAccount.class,
    Compensation.class,
    ItemClass.class,
    WarehouseBin.class,
    KitComponent.class,
    SalesItemWarehouse.class,
    SalesItemWarehouseBin.class,
    PricingDetail.class,
    UofMScheduleDetail.class,
    InventoryBase.class,
    InventoryLineBase.class,
    InventoryBinBase.class,
    InventoryLotBase.class,
    InventorySerialBase.class,
    ManufacturingOrderDocument.class,
    ProjectHeaderBase.class,
    ProjectEmployeeExpenseLineTax.class,
    ProjectLineBase.class,
    ProjectDistributionBase.class,
    ProjectChangeOrderBudget.class,
    ProjectChangeOrderFee.class,
    ProjectChangeOrderFeeLine.class,
    ProjectChangeOrderFeeLineSchedule.class,
    ProjectContractBillingCycle.class,
    ProjectContractAccountType.class,
    ProjectBillingCycle.class,
    ProjectAccountType.class,
    ProjectEquipmentItem.class,
    ProjectFee.class,
    ProjectFeeSchedule.class,
    PurchaseOrderLine.class,
    PurchaseUserDefined.class,
    PurchaseDistribution.class,
    PurchaseReceiptLine.class,
    PurchaseLotDetail.class,
    PurchaseSerialDetail.class,
    PurchaseBinDetail.class,
    PurchaseTax.class,
    PurchaseInvoiceLine.class,
    PurchaseInvoiceApplyReceipt.class,
    CorporateAccountMember.class,
    AddressBase.class,
    ReceivablesDocument.class,
    ReceivablesCommission.class,
    PayablesDocument.class,
    SalesDocument.class,
    SalesCommission.class,
    SalesProcessHold.class,
    Tax.class,
    SalesTrackingNumber.class,
    SalesBin.class,
    SalesComponent.class,
    SalesLine.class,
    SalesPayment.class,
    Distribution.class
})
public abstract class BusinessObject {

    @XmlElement(name = "Extensions")
    protected ExtensionList extensions;

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionList }
     *     
     */
    public ExtensionList getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionList }
     *     
     */
    public void setExtensions(ExtensionList value) {
        this.extensions = value;
    }

}
