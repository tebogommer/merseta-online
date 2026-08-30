
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectChangeOrderBudget complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectChangeOrderBudget"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderBudgetKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="CostCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="LineItemSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UofMScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UofMScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="PreviousContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PreviousContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PreviousPayCodeSalaryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderPayCodeSalaryKey" minOccurs="0"/&gt;
 *         &lt;element name="PayCodeSalaryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderPayCodeSalaryKey" minOccurs="0"/&gt;
 *         &lt;element name="PreviousPayCodeHourlyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderPayCodeHourlyKey" minOccurs="0"/&gt;
 *         &lt;element name="PayCodeHourlyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderPayCodeHourlyKey" minOccurs="0"/&gt;
 *         &lt;element name="ProfitType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitType"/&gt;
 *         &lt;element name="BillingType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BillingType"/&gt;
 *         &lt;element name="PreviousPurchaseTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchasingTaxBasis"/&gt;
 *         &lt;element name="PurchaseTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchasingTaxBasis"/&gt;
 *         &lt;element name="PreviousTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="PreviousSalesTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="SalesTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="PreviousSalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="VarianceQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="PreviousQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="VarianceUnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousUnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceOverheadAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalOverheadAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceOverheadPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="OverheadPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="PreviousOverheadPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="PreviousTotalOverheadAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceMarkupPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="ProfitPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="PreviousProfitPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="VarianceProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceTotalProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousTotalProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceTotalBillingAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalBillingAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousTotalBillingAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="OriginalBudgetAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="OriginalProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousBaselineQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="BaselineQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="PreviousBaselineUnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BaselineUnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousBaselineOverheadCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BaselineOverheadCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousBaselineProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BaselineProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="InitialQuoteAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FinalQuoteAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="QuotePreparedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="QuoteApprovedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RevisedBudgetTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RevisedProjectTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ApprovalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsClosed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ActualReceiptAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ActualWriteoffAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ActualWriteoffTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Posted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectPostedAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectChangeOrderBudget", propOrder = {
    "key",
    "projectKey",
    "costCategoryKey",
    "lineItemSequenceNumber",
    "uofM",
    "uofMScheduleKey",
    "previousContractBeginDate",
    "contractBeginDate",
    "previousContractEndDate",
    "contractEndDate",
    "previousPayCodeSalaryKey",
    "payCodeSalaryKey",
    "previousPayCodeHourlyKey",
    "payCodeHourlyKey",
    "profitType",
    "billingType",
    "previousPurchaseTaxBasis",
    "purchaseTaxBasis",
    "previousTaxScheduleKey",
    "purchaseTaxScheduleKey",
    "previousSalesTaxBasis",
    "salesTaxBasis",
    "previousSalesTaxScheduleKey",
    "salesTaxScheduleKey",
    "varianceQuantity",
    "quantity",
    "previousQuantity",
    "varianceUnitCost",
    "unitCost",
    "previousUnitCost",
    "varianceTotalCost",
    "varianceOverheadAmount",
    "totalOverheadAmount",
    "varianceOverheadPercent",
    "overheadPercent",
    "previousOverheadPercent",
    "previousTotalOverheadAmount",
    "totalCost",
    "previousTotalCost",
    "varianceMarkupPercent",
    "profitPercent",
    "previousProfitPercent",
    "varianceProfitAmount",
    "profitAmount",
    "previousProfitAmount",
    "varianceTotalProfitAmount",
    "totalProfitAmount",
    "previousTotalProfitAmount",
    "varianceTotalBillingAmount",
    "totalBillingAmount",
    "previousTotalBillingAmount",
    "originalBudgetAmount",
    "originalProjectAmount",
    "projectAmount",
    "previousProjectAmount",
    "varianceProjectAmount",
    "previousBaselineQuantity",
    "baselineQuantity",
    "previousBaselineUnitCost",
    "baselineUnitCost",
    "previousBaselineOverheadCost",
    "baselineOverheadCost",
    "previousBaselineProfitAmount",
    "baselineProfitAmount",
    "initialQuoteAmount",
    "finalQuoteAmount",
    "quotePreparedBy",
    "quoteApprovedBy",
    "revisedBudgetTotalAmount",
    "revisedProjectTotalAmount",
    "sequenceNumber",
    "approvalDate",
    "isClosed",
    "actualReceiptAmount",
    "actualWriteoffAmount",
    "actualWriteoffTaxAmount",
    "posted"
})
public class ProjectChangeOrderBudget
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectChangeOrderBudgetKey key;
    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "CostCategoryKey")
    protected CostCategoryKey costCategoryKey;
    @XmlElement(name = "LineItemSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer lineItemSequenceNumber;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "UofMScheduleKey")
    protected UofMScheduleKey uofMScheduleKey;
    @XmlElement(name = "PreviousContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar previousContractBeginDate;
    @XmlElement(name = "ContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractBeginDate;
    @XmlElement(name = "PreviousContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar previousContractEndDate;
    @XmlElement(name = "ContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "PreviousPayCodeSalaryKey")
    protected ProjectChangeOrderPayCodeSalaryKey previousPayCodeSalaryKey;
    @XmlElement(name = "PayCodeSalaryKey")
    protected ProjectChangeOrderPayCodeSalaryKey payCodeSalaryKey;
    @XmlElement(name = "PreviousPayCodeHourlyKey")
    protected ProjectChangeOrderPayCodeHourlyKey previousPayCodeHourlyKey;
    @XmlElement(name = "PayCodeHourlyKey")
    protected ProjectChangeOrderPayCodeHourlyKey payCodeHourlyKey;
    @XmlElement(name = "ProfitType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProfitType profitType;
    @XmlElement(name = "BillingType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected BillingType billingType;
    @XmlElement(name = "PreviousPurchaseTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchasingTaxBasis previousPurchaseTaxBasis;
    @XmlElement(name = "PurchaseTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchasingTaxBasis purchaseTaxBasis;
    @XmlElement(name = "PreviousTaxScheduleKey")
    protected TaxScheduleKey previousTaxScheduleKey;
    @XmlElement(name = "PurchaseTaxScheduleKey")
    protected TaxScheduleKey purchaseTaxScheduleKey;
    @XmlElement(name = "PreviousSalesTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis previousSalesTaxBasis;
    @XmlElement(name = "SalesTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis salesTaxBasis;
    @XmlElement(name = "PreviousSalesTaxScheduleKey")
    protected TaxScheduleKey previousSalesTaxScheduleKey;
    @XmlElement(name = "SalesTaxScheduleKey")
    protected TaxScheduleKey salesTaxScheduleKey;
    @XmlElement(name = "VarianceQuantity")
    protected Quantity varianceQuantity;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "PreviousQuantity")
    protected Quantity previousQuantity;
    @XmlElement(name = "VarianceUnitCost")
    protected MoneyAmount varianceUnitCost;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "PreviousUnitCost")
    protected MoneyAmount previousUnitCost;
    @XmlElement(name = "VarianceTotalCost")
    protected MoneyAmount varianceTotalCost;
    @XmlElement(name = "VarianceOverheadAmount")
    protected MoneyAmount varianceOverheadAmount;
    @XmlElement(name = "TotalOverheadAmount")
    protected MoneyAmount totalOverheadAmount;
    @XmlElement(name = "VarianceOverheadPercent")
    protected Percent varianceOverheadPercent;
    @XmlElement(name = "OverheadPercent")
    protected Percent overheadPercent;
    @XmlElement(name = "PreviousOverheadPercent")
    protected Percent previousOverheadPercent;
    @XmlElement(name = "PreviousTotalOverheadAmount")
    protected MoneyAmount previousTotalOverheadAmount;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "PreviousTotalCost")
    protected MoneyAmount previousTotalCost;
    @XmlElement(name = "VarianceMarkupPercent")
    protected Percent varianceMarkupPercent;
    @XmlElement(name = "ProfitPercent")
    protected Percent profitPercent;
    @XmlElement(name = "PreviousProfitPercent")
    protected Percent previousProfitPercent;
    @XmlElement(name = "VarianceProfitAmount")
    protected MoneyAmount varianceProfitAmount;
    @XmlElement(name = "ProfitAmount")
    protected MoneyAmount profitAmount;
    @XmlElement(name = "PreviousProfitAmount")
    protected MoneyAmount previousProfitAmount;
    @XmlElement(name = "VarianceTotalProfitAmount")
    protected MoneyAmount varianceTotalProfitAmount;
    @XmlElement(name = "TotalProfitAmount")
    protected MoneyAmount totalProfitAmount;
    @XmlElement(name = "PreviousTotalProfitAmount")
    protected MoneyAmount previousTotalProfitAmount;
    @XmlElement(name = "VarianceTotalBillingAmount")
    protected MoneyAmount varianceTotalBillingAmount;
    @XmlElement(name = "TotalBillingAmount")
    protected MoneyAmount totalBillingAmount;
    @XmlElement(name = "PreviousTotalBillingAmount")
    protected MoneyAmount previousTotalBillingAmount;
    @XmlElement(name = "OriginalBudgetAmount")
    protected MoneyAmount originalBudgetAmount;
    @XmlElement(name = "OriginalProjectAmount")
    protected MoneyAmount originalProjectAmount;
    @XmlElement(name = "ProjectAmount")
    protected MoneyAmount projectAmount;
    @XmlElement(name = "PreviousProjectAmount")
    protected MoneyAmount previousProjectAmount;
    @XmlElement(name = "VarianceProjectAmount")
    protected MoneyAmount varianceProjectAmount;
    @XmlElement(name = "PreviousBaselineQuantity")
    protected Quantity previousBaselineQuantity;
    @XmlElement(name = "BaselineQuantity")
    protected Quantity baselineQuantity;
    @XmlElement(name = "PreviousBaselineUnitCost")
    protected MoneyAmount previousBaselineUnitCost;
    @XmlElement(name = "BaselineUnitCost")
    protected MoneyAmount baselineUnitCost;
    @XmlElement(name = "PreviousBaselineOverheadCost")
    protected MoneyAmount previousBaselineOverheadCost;
    @XmlElement(name = "BaselineOverheadCost")
    protected MoneyAmount baselineOverheadCost;
    @XmlElement(name = "PreviousBaselineProfitAmount")
    protected MoneyAmount previousBaselineProfitAmount;
    @XmlElement(name = "BaselineProfitAmount")
    protected MoneyAmount baselineProfitAmount;
    @XmlElement(name = "InitialQuoteAmount")
    protected MoneyAmount initialQuoteAmount;
    @XmlElement(name = "FinalQuoteAmount")
    protected MoneyAmount finalQuoteAmount;
    @XmlElement(name = "QuotePreparedBy")
    protected String quotePreparedBy;
    @XmlElement(name = "QuoteApprovedBy")
    protected String quoteApprovedBy;
    @XmlElement(name = "RevisedBudgetTotalAmount")
    protected MoneyAmount revisedBudgetTotalAmount;
    @XmlElement(name = "RevisedProjectTotalAmount")
    protected MoneyAmount revisedProjectTotalAmount;
    @XmlElement(name = "SequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer sequenceNumber;
    @XmlElement(name = "ApprovalDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar approvalDate;
    @XmlElement(name = "IsClosed", required = true, type = Boolean.class, nillable = true)
    protected Boolean isClosed;
    @XmlElement(name = "ActualReceiptAmount")
    protected MoneyAmount actualReceiptAmount;
    @XmlElement(name = "ActualWriteoffAmount")
    protected MoneyAmount actualWriteoffAmount;
    @XmlElement(name = "ActualWriteoffTaxAmount")
    protected MoneyAmount actualWriteoffTaxAmount;
    @XmlElement(name = "Posted")
    protected ProjectPostedAmount posted;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderBudgetKey }
     *     
     */
    public ProjectChangeOrderBudgetKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderBudgetKey }
     *     
     */
    public void setKey(ProjectChangeOrderBudgetKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the projectKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectKey }
     *     
     */
    public ProjectKey getProjectKey() {
        return projectKey;
    }

    /**
     * Sets the value of the projectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectKey }
     *     
     */
    public void setProjectKey(ProjectKey value) {
        this.projectKey = value;
    }

    /**
     * Gets the value of the costCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link CostCategoryKey }
     *     
     */
    public CostCategoryKey getCostCategoryKey() {
        return costCategoryKey;
    }

    /**
     * Sets the value of the costCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostCategoryKey }
     *     
     */
    public void setCostCategoryKey(CostCategoryKey value) {
        this.costCategoryKey = value;
    }

    /**
     * Gets the value of the lineItemSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLineItemSequenceNumber() {
        return lineItemSequenceNumber;
    }

    /**
     * Sets the value of the lineItemSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLineItemSequenceNumber(Integer value) {
        this.lineItemSequenceNumber = value;
    }

    /**
     * Gets the value of the uofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUofM() {
        return uofM;
    }

    /**
     * Sets the value of the uofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUofM(String value) {
        this.uofM = value;
    }

    /**
     * Gets the value of the uofMScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link UofMScheduleKey }
     *     
     */
    public UofMScheduleKey getUofMScheduleKey() {
        return uofMScheduleKey;
    }

    /**
     * Sets the value of the uofMScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UofMScheduleKey }
     *     
     */
    public void setUofMScheduleKey(UofMScheduleKey value) {
        this.uofMScheduleKey = value;
    }

    /**
     * Gets the value of the previousContractBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreviousContractBeginDate() {
        return previousContractBeginDate;
    }

    /**
     * Sets the value of the previousContractBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreviousContractBeginDate(XMLGregorianCalendar value) {
        this.previousContractBeginDate = value;
    }

    /**
     * Gets the value of the contractBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractBeginDate() {
        return contractBeginDate;
    }

    /**
     * Sets the value of the contractBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractBeginDate(XMLGregorianCalendar value) {
        this.contractBeginDate = value;
    }

    /**
     * Gets the value of the previousContractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreviousContractEndDate() {
        return previousContractEndDate;
    }

    /**
     * Sets the value of the previousContractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreviousContractEndDate(XMLGregorianCalendar value) {
        this.previousContractEndDate = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the previousPayCodeSalaryKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderPayCodeSalaryKey }
     *     
     */
    public ProjectChangeOrderPayCodeSalaryKey getPreviousPayCodeSalaryKey() {
        return previousPayCodeSalaryKey;
    }

    /**
     * Sets the value of the previousPayCodeSalaryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderPayCodeSalaryKey }
     *     
     */
    public void setPreviousPayCodeSalaryKey(ProjectChangeOrderPayCodeSalaryKey value) {
        this.previousPayCodeSalaryKey = value;
    }

    /**
     * Gets the value of the payCodeSalaryKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderPayCodeSalaryKey }
     *     
     */
    public ProjectChangeOrderPayCodeSalaryKey getPayCodeSalaryKey() {
        return payCodeSalaryKey;
    }

    /**
     * Sets the value of the payCodeSalaryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderPayCodeSalaryKey }
     *     
     */
    public void setPayCodeSalaryKey(ProjectChangeOrderPayCodeSalaryKey value) {
        this.payCodeSalaryKey = value;
    }

    /**
     * Gets the value of the previousPayCodeHourlyKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderPayCodeHourlyKey }
     *     
     */
    public ProjectChangeOrderPayCodeHourlyKey getPreviousPayCodeHourlyKey() {
        return previousPayCodeHourlyKey;
    }

    /**
     * Sets the value of the previousPayCodeHourlyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderPayCodeHourlyKey }
     *     
     */
    public void setPreviousPayCodeHourlyKey(ProjectChangeOrderPayCodeHourlyKey value) {
        this.previousPayCodeHourlyKey = value;
    }

    /**
     * Gets the value of the payCodeHourlyKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderPayCodeHourlyKey }
     *     
     */
    public ProjectChangeOrderPayCodeHourlyKey getPayCodeHourlyKey() {
        return payCodeHourlyKey;
    }

    /**
     * Sets the value of the payCodeHourlyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderPayCodeHourlyKey }
     *     
     */
    public void setPayCodeHourlyKey(ProjectChangeOrderPayCodeHourlyKey value) {
        this.payCodeHourlyKey = value;
    }

    /**
     * Gets the value of the profitType property.
     * 
     * @return
     *     possible object is
     *     {@link ProfitType }
     *     
     */
    public ProfitType getProfitType() {
        return profitType;
    }

    /**
     * Sets the value of the profitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfitType }
     *     
     */
    public void setProfitType(ProfitType value) {
        this.profitType = value;
    }

    /**
     * Gets the value of the billingType property.
     * 
     * @return
     *     possible object is
     *     {@link BillingType }
     *     
     */
    public BillingType getBillingType() {
        return billingType;
    }

    /**
     * Sets the value of the billingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingType }
     *     
     */
    public void setBillingType(BillingType value) {
        this.billingType = value;
    }

    /**
     * Gets the value of the previousPurchaseTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public PurchasingTaxBasis getPreviousPurchaseTaxBasis() {
        return previousPurchaseTaxBasis;
    }

    /**
     * Sets the value of the previousPurchaseTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public void setPreviousPurchaseTaxBasis(PurchasingTaxBasis value) {
        this.previousPurchaseTaxBasis = value;
    }

    /**
     * Gets the value of the purchaseTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public PurchasingTaxBasis getPurchaseTaxBasis() {
        return purchaseTaxBasis;
    }

    /**
     * Sets the value of the purchaseTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public void setPurchaseTaxBasis(PurchasingTaxBasis value) {
        this.purchaseTaxBasis = value;
    }

    /**
     * Gets the value of the previousTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPreviousTaxScheduleKey() {
        return previousTaxScheduleKey;
    }

    /**
     * Sets the value of the previousTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPreviousTaxScheduleKey(TaxScheduleKey value) {
        this.previousTaxScheduleKey = value;
    }

    /**
     * Gets the value of the purchaseTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPurchaseTaxScheduleKey() {
        return purchaseTaxScheduleKey;
    }

    /**
     * Sets the value of the purchaseTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPurchaseTaxScheduleKey(TaxScheduleKey value) {
        this.purchaseTaxScheduleKey = value;
    }

    /**
     * Gets the value of the previousSalesTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getPreviousSalesTaxBasis() {
        return previousSalesTaxBasis;
    }

    /**
     * Sets the value of the previousSalesTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setPreviousSalesTaxBasis(SalesTaxBasis value) {
        this.previousSalesTaxBasis = value;
    }

    /**
     * Gets the value of the salesTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getSalesTaxBasis() {
        return salesTaxBasis;
    }

    /**
     * Sets the value of the salesTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setSalesTaxBasis(SalesTaxBasis value) {
        this.salesTaxBasis = value;
    }

    /**
     * Gets the value of the previousSalesTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPreviousSalesTaxScheduleKey() {
        return previousSalesTaxScheduleKey;
    }

    /**
     * Sets the value of the previousSalesTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPreviousSalesTaxScheduleKey(TaxScheduleKey value) {
        this.previousSalesTaxScheduleKey = value;
    }

    /**
     * Gets the value of the salesTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getSalesTaxScheduleKey() {
        return salesTaxScheduleKey;
    }

    /**
     * Sets the value of the salesTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setSalesTaxScheduleKey(TaxScheduleKey value) {
        this.salesTaxScheduleKey = value;
    }

    /**
     * Gets the value of the varianceQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getVarianceQuantity() {
        return varianceQuantity;
    }

    /**
     * Sets the value of the varianceQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setVarianceQuantity(Quantity value) {
        this.varianceQuantity = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the previousQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getPreviousQuantity() {
        return previousQuantity;
    }

    /**
     * Sets the value of the previousQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setPreviousQuantity(Quantity value) {
        this.previousQuantity = value;
    }

    /**
     * Gets the value of the varianceUnitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceUnitCost() {
        return varianceUnitCost;
    }

    /**
     * Sets the value of the varianceUnitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceUnitCost(MoneyAmount value) {
        this.varianceUnitCost = value;
    }

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitCost(MoneyAmount value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the previousUnitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousUnitCost() {
        return previousUnitCost;
    }

    /**
     * Sets the value of the previousUnitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousUnitCost(MoneyAmount value) {
        this.previousUnitCost = value;
    }

    /**
     * Gets the value of the varianceTotalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceTotalCost() {
        return varianceTotalCost;
    }

    /**
     * Sets the value of the varianceTotalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceTotalCost(MoneyAmount value) {
        this.varianceTotalCost = value;
    }

    /**
     * Gets the value of the varianceOverheadAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceOverheadAmount() {
        return varianceOverheadAmount;
    }

    /**
     * Sets the value of the varianceOverheadAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceOverheadAmount(MoneyAmount value) {
        this.varianceOverheadAmount = value;
    }

    /**
     * Gets the value of the totalOverheadAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalOverheadAmount() {
        return totalOverheadAmount;
    }

    /**
     * Sets the value of the totalOverheadAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalOverheadAmount(MoneyAmount value) {
        this.totalOverheadAmount = value;
    }

    /**
     * Gets the value of the varianceOverheadPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getVarianceOverheadPercent() {
        return varianceOverheadPercent;
    }

    /**
     * Sets the value of the varianceOverheadPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setVarianceOverheadPercent(Percent value) {
        this.varianceOverheadPercent = value;
    }

    /**
     * Gets the value of the overheadPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getOverheadPercent() {
        return overheadPercent;
    }

    /**
     * Sets the value of the overheadPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setOverheadPercent(Percent value) {
        this.overheadPercent = value;
    }

    /**
     * Gets the value of the previousOverheadPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getPreviousOverheadPercent() {
        return previousOverheadPercent;
    }

    /**
     * Sets the value of the previousOverheadPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setPreviousOverheadPercent(Percent value) {
        this.previousOverheadPercent = value;
    }

    /**
     * Gets the value of the previousTotalOverheadAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousTotalOverheadAmount() {
        return previousTotalOverheadAmount;
    }

    /**
     * Sets the value of the previousTotalOverheadAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousTotalOverheadAmount(MoneyAmount value) {
        this.previousTotalOverheadAmount = value;
    }

    /**
     * Gets the value of the totalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCost(MoneyAmount value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the previousTotalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousTotalCost() {
        return previousTotalCost;
    }

    /**
     * Sets the value of the previousTotalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousTotalCost(MoneyAmount value) {
        this.previousTotalCost = value;
    }

    /**
     * Gets the value of the varianceMarkupPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getVarianceMarkupPercent() {
        return varianceMarkupPercent;
    }

    /**
     * Sets the value of the varianceMarkupPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setVarianceMarkupPercent(Percent value) {
        this.varianceMarkupPercent = value;
    }

    /**
     * Gets the value of the profitPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getProfitPercent() {
        return profitPercent;
    }

    /**
     * Sets the value of the profitPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setProfitPercent(Percent value) {
        this.profitPercent = value;
    }

    /**
     * Gets the value of the previousProfitPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getPreviousProfitPercent() {
        return previousProfitPercent;
    }

    /**
     * Sets the value of the previousProfitPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setPreviousProfitPercent(Percent value) {
        this.previousProfitPercent = value;
    }

    /**
     * Gets the value of the varianceProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceProfitAmount() {
        return varianceProfitAmount;
    }

    /**
     * Sets the value of the varianceProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceProfitAmount(MoneyAmount value) {
        this.varianceProfitAmount = value;
    }

    /**
     * Gets the value of the profitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProfitAmount() {
        return profitAmount;
    }

    /**
     * Sets the value of the profitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProfitAmount(MoneyAmount value) {
        this.profitAmount = value;
    }

    /**
     * Gets the value of the previousProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousProfitAmount() {
        return previousProfitAmount;
    }

    /**
     * Sets the value of the previousProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousProfitAmount(MoneyAmount value) {
        this.previousProfitAmount = value;
    }

    /**
     * Gets the value of the varianceTotalProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceTotalProfitAmount() {
        return varianceTotalProfitAmount;
    }

    /**
     * Sets the value of the varianceTotalProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceTotalProfitAmount(MoneyAmount value) {
        this.varianceTotalProfitAmount = value;
    }

    /**
     * Gets the value of the totalProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalProfitAmount() {
        return totalProfitAmount;
    }

    /**
     * Sets the value of the totalProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalProfitAmount(MoneyAmount value) {
        this.totalProfitAmount = value;
    }

    /**
     * Gets the value of the previousTotalProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousTotalProfitAmount() {
        return previousTotalProfitAmount;
    }

    /**
     * Sets the value of the previousTotalProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousTotalProfitAmount(MoneyAmount value) {
        this.previousTotalProfitAmount = value;
    }

    /**
     * Gets the value of the varianceTotalBillingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceTotalBillingAmount() {
        return varianceTotalBillingAmount;
    }

    /**
     * Sets the value of the varianceTotalBillingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceTotalBillingAmount(MoneyAmount value) {
        this.varianceTotalBillingAmount = value;
    }

    /**
     * Gets the value of the totalBillingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalBillingAmount() {
        return totalBillingAmount;
    }

    /**
     * Sets the value of the totalBillingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalBillingAmount(MoneyAmount value) {
        this.totalBillingAmount = value;
    }

    /**
     * Gets the value of the previousTotalBillingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousTotalBillingAmount() {
        return previousTotalBillingAmount;
    }

    /**
     * Sets the value of the previousTotalBillingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousTotalBillingAmount(MoneyAmount value) {
        this.previousTotalBillingAmount = value;
    }

    /**
     * Gets the value of the originalBudgetAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getOriginalBudgetAmount() {
        return originalBudgetAmount;
    }

    /**
     * Sets the value of the originalBudgetAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setOriginalBudgetAmount(MoneyAmount value) {
        this.originalBudgetAmount = value;
    }

    /**
     * Gets the value of the originalProjectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getOriginalProjectAmount() {
        return originalProjectAmount;
    }

    /**
     * Sets the value of the originalProjectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setOriginalProjectAmount(MoneyAmount value) {
        this.originalProjectAmount = value;
    }

    /**
     * Gets the value of the projectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProjectAmount() {
        return projectAmount;
    }

    /**
     * Sets the value of the projectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProjectAmount(MoneyAmount value) {
        this.projectAmount = value;
    }

    /**
     * Gets the value of the previousProjectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousProjectAmount() {
        return previousProjectAmount;
    }

    /**
     * Sets the value of the previousProjectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousProjectAmount(MoneyAmount value) {
        this.previousProjectAmount = value;
    }

    /**
     * Gets the value of the varianceProjectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceProjectAmount() {
        return varianceProjectAmount;
    }

    /**
     * Sets the value of the varianceProjectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceProjectAmount(MoneyAmount value) {
        this.varianceProjectAmount = value;
    }

    /**
     * Gets the value of the previousBaselineQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getPreviousBaselineQuantity() {
        return previousBaselineQuantity;
    }

    /**
     * Sets the value of the previousBaselineQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setPreviousBaselineQuantity(Quantity value) {
        this.previousBaselineQuantity = value;
    }

    /**
     * Gets the value of the baselineQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getBaselineQuantity() {
        return baselineQuantity;
    }

    /**
     * Sets the value of the baselineQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setBaselineQuantity(Quantity value) {
        this.baselineQuantity = value;
    }

    /**
     * Gets the value of the previousBaselineUnitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousBaselineUnitCost() {
        return previousBaselineUnitCost;
    }

    /**
     * Sets the value of the previousBaselineUnitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousBaselineUnitCost(MoneyAmount value) {
        this.previousBaselineUnitCost = value;
    }

    /**
     * Gets the value of the baselineUnitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBaselineUnitCost() {
        return baselineUnitCost;
    }

    /**
     * Sets the value of the baselineUnitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBaselineUnitCost(MoneyAmount value) {
        this.baselineUnitCost = value;
    }

    /**
     * Gets the value of the previousBaselineOverheadCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousBaselineOverheadCost() {
        return previousBaselineOverheadCost;
    }

    /**
     * Sets the value of the previousBaselineOverheadCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousBaselineOverheadCost(MoneyAmount value) {
        this.previousBaselineOverheadCost = value;
    }

    /**
     * Gets the value of the baselineOverheadCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBaselineOverheadCost() {
        return baselineOverheadCost;
    }

    /**
     * Sets the value of the baselineOverheadCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBaselineOverheadCost(MoneyAmount value) {
        this.baselineOverheadCost = value;
    }

    /**
     * Gets the value of the previousBaselineProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousBaselineProfitAmount() {
        return previousBaselineProfitAmount;
    }

    /**
     * Sets the value of the previousBaselineProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousBaselineProfitAmount(MoneyAmount value) {
        this.previousBaselineProfitAmount = value;
    }

    /**
     * Gets the value of the baselineProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBaselineProfitAmount() {
        return baselineProfitAmount;
    }

    /**
     * Sets the value of the baselineProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBaselineProfitAmount(MoneyAmount value) {
        this.baselineProfitAmount = value;
    }

    /**
     * Gets the value of the initialQuoteAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getInitialQuoteAmount() {
        return initialQuoteAmount;
    }

    /**
     * Sets the value of the initialQuoteAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setInitialQuoteAmount(MoneyAmount value) {
        this.initialQuoteAmount = value;
    }

    /**
     * Gets the value of the finalQuoteAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getFinalQuoteAmount() {
        return finalQuoteAmount;
    }

    /**
     * Sets the value of the finalQuoteAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setFinalQuoteAmount(MoneyAmount value) {
        this.finalQuoteAmount = value;
    }

    /**
     * Gets the value of the quotePreparedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuotePreparedBy() {
        return quotePreparedBy;
    }

    /**
     * Sets the value of the quotePreparedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuotePreparedBy(String value) {
        this.quotePreparedBy = value;
    }

    /**
     * Gets the value of the quoteApprovedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuoteApprovedBy() {
        return quoteApprovedBy;
    }

    /**
     * Sets the value of the quoteApprovedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuoteApprovedBy(String value) {
        this.quoteApprovedBy = value;
    }

    /**
     * Gets the value of the revisedBudgetTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRevisedBudgetTotalAmount() {
        return revisedBudgetTotalAmount;
    }

    /**
     * Sets the value of the revisedBudgetTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRevisedBudgetTotalAmount(MoneyAmount value) {
        this.revisedBudgetTotalAmount = value;
    }

    /**
     * Gets the value of the revisedProjectTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRevisedProjectTotalAmount() {
        return revisedProjectTotalAmount;
    }

    /**
     * Sets the value of the revisedProjectTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRevisedProjectTotalAmount(MoneyAmount value) {
        this.revisedProjectTotalAmount = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequenceNumber(Integer value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the approvalDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApprovalDate() {
        return approvalDate;
    }

    /**
     * Sets the value of the approvalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApprovalDate(XMLGregorianCalendar value) {
        this.approvalDate = value;
    }

    /**
     * Gets the value of the isClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsClosed() {
        return isClosed;
    }

    /**
     * Sets the value of the isClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsClosed(Boolean value) {
        this.isClosed = value;
    }

    /**
     * Gets the value of the actualReceiptAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getActualReceiptAmount() {
        return actualReceiptAmount;
    }

    /**
     * Sets the value of the actualReceiptAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setActualReceiptAmount(MoneyAmount value) {
        this.actualReceiptAmount = value;
    }

    /**
     * Gets the value of the actualWriteoffAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getActualWriteoffAmount() {
        return actualWriteoffAmount;
    }

    /**
     * Sets the value of the actualWriteoffAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setActualWriteoffAmount(MoneyAmount value) {
        this.actualWriteoffAmount = value;
    }

    /**
     * Gets the value of the actualWriteoffTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getActualWriteoffTaxAmount() {
        return actualWriteoffTaxAmount;
    }

    /**
     * Sets the value of the actualWriteoffTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setActualWriteoffTaxAmount(MoneyAmount value) {
        this.actualWriteoffTaxAmount = value;
    }

    /**
     * Gets the value of the posted property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectPostedAmount }
     *     
     */
    public ProjectPostedAmount getPosted() {
        return posted;
    }

    /**
     * Sets the value of the posted property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectPostedAmount }
     *     
     */
    public void setPosted(ProjectPostedAmount value) {
        this.posted = value;
    }

}
