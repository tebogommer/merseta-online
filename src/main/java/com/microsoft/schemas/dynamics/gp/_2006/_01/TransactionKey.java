
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GreatPlainsKey"&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionKey")
@XmlSeeAlso({
    ServiceDocumentKey.class,
    GLTransactionKey.class,
    InventoryKey.class,
    ProjectMiscellaneousLogKey.class,
    ProjectTimesheetKey.class,
    ProjectEmployeeExpenseKey.class,
    PayablesDocumentKey.class,
    PurchaseTransactionKey.class,
    ReceivablesDocumentKey.class,
    SalesDocumentKey.class,
    SalespersonCommissionsKey.class,
    ServiceLineKey.class,
    SalesLineKey.class,
    ServiceLineDetailKey.class,
    SalesComponentLotKey.class,
    SalesComponentKey.class,
    SalesLineLotKey.class,
    SalesComponentSerialKey.class,
    SalesLineSerialKey.class,
    ServiceSerialLotKey.class,
    PurchaseTransactionLineKey.class,
    InventoryLineKey.class,
    InventoryBinKey.class,
    InventoryLotKey.class,
    InventorySerialKey.class,
    ProjectEquipmentRateTableKey.class,
    ProjectLaborRateTableKey.class,
    PayablesDistributionKey.class,
    PayablesTaxKey.class,
    PurchaseUserDefinedKey.class,
    PurchaseDistributionKey.class,
    PurchaseLotDetailKey.class,
    PurchaseSerialDetailKey.class,
    PurchaseBinDetailKey.class,
    PurchaseInvoiceApplyReceiptKey.class,
    ReceivablesTaxKey.class,
    ReceivablesDistributionKey.class,
    ReceivablesCommissionKey.class,
    SalesCommissionKey.class,
    SalesProcessHoldKey.class,
    SalesLineTaxKey.class,
    SalesDocumentTaxKey.class,
    SalesTrackingNumberKey.class,
    SalesLineBinKey.class,
    SalesComponentBinKey.class,
    SalesPaymentKey.class,
    SalesDistributionKey.class
})
public abstract class TransactionKey
    extends GreatPlainsKey
{


}
