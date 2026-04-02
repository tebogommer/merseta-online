import React from "react";
import {
  Document,
  Page,
  Text,
  View,
  StyleSheet,
  Image,
  Font,
} from "@react-pdf/renderer";

// Register fonts if needed (using standard Helvetica for now)
// Font.register({ family: 'Inter', src: '...' });

const styles = StyleSheet.create({
  page: {
    flexDirection: "column",
    backgroundColor: "#FFFFFF",
    padding: 40,
    fontFamily: "Helvetica",
  },
  header: {
    marginBottom: 20,
    borderBottomWidth: 2,
    borderBottomColor: "#004B87", // MerSETA Blue
    paddingBottom: 10,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  logo: {
    width: 120,
  },
  titleContainer: {
    textAlign: "right",
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#004B87",
  },
  subtitle: {
    fontSize: 10,
    color: "#666666",
    marginTop: 2,
  },
  section: {
    marginVertical: 15,
  },
  sectionTitle: {
    fontSize: 12,
    fontWeight: "bold",
    backgroundColor: "#F3F4F6",
    padding: 5,
    marginBottom: 10,
    textTransform: "uppercase",
  },
  row: {
    flexDirection: "row",
    marginBottom: 5,
  },
  label: {
    width: 150,
    fontSize: 10,
    fontWeight: "bold",
    color: "#374151",
  },
  value: {
    flex: 1,
    fontSize: 10,
    color: "#111827",
  },
  body: {
    fontSize: 11,
    lineHeight: 1.5,
    color: "#111827",
    marginBottom: 10,
  },
  footer: {
    position: "absolute",
    bottom: 30,
    left: 40,
    right: 40,
    borderTopWidth: 1,
    borderTopColor: "#E5E7EB",
    paddingTop: 10,
    textAlign: "center",
    fontSize: 9,
    color: "#9CA3AF",
  },
  signatureSection: {
    marginTop: 50,
    flexDirection: "row",
    justifyContent: "space-between",
  },
  signatureBox: {
    width: "45%",
    borderTopWidth: 1,
    borderTopColor: "#000000",
    paddingTop: 5,
    textAlign: "center",
  },
});

export interface AccreditationTemplateProps {
  organisationName: string;
  sdlNumber: string;
  accreditationNumber: string;
  expiryDate: string;
  decisionDate: string;
}

/**
 * AccreditationCertificateTemplate
 * Standard MerSETA template for Primary Accreditation letters.
 */
export const AccreditationCertificateTemplate = ({
  organisationName,
  sdlNumber,
  accreditationNumber,
  expiryDate,
  decisionDate,
}: AccreditationTemplateProps) => (
  <Document>
    <Page size="A4" style={styles.page}>
      {/* Header */}
      <View style={styles.header}>
        <View>
          <Text style={styles.title}>Letter of Accreditation</Text>
          <Text style={styles.subtitle}>merSETA Operations Division</Text>
        </View>
        {/* Placeholder for MerSETA Logo */}
        <Text style={{ fontSize: 8, color: "#999" }}>[merSETA OFFICIAL LOGO]</Text>
      </View>

      {/* Intro */}
      <View style={styles.section}>
        <Text style={styles.body}>To: The Principal / CEO</Text>
        <Text style={styles.body}>{organisationName}</Text>
        <Text style={styles.body}>SDL Number: {sdlNumber}</Text>
      </View>

      <View style={styles.section}>
        <Text style={styles.body}>
          Dear Sir/Madam,
        </Text>
        <Text style={styles.body}>
          The merSETA Quality Management System Committee (QMSC) is pleased to inform you that your 
          application for Primary Accreditation has been successful. This accreditation confirms 
          your status as a Skills Development Provider (SDP) for the following scope:
        </Text>
      </View>

      {/* Details Table */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Accreditation Details</Text>
        <View style={styles.row}>
          <Text style={styles.label}>Accreditation Number:</Text>
          <Text style={styles.value}>{accreditationNumber}</Text>
        </View>
        <View style={styles.row}>
          <Text style={styles.label}>Decision Date:</Text>
          <Text style={styles.value}>{decisionDate}</Text>
        </View>
        <View style={styles.row}>
          <Text style={styles.label}>Expiry Date:</Text>
          <Text style={styles.value}>{expiryDate}</Text>
        </View>
      </View>

      {/* Conditions */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>General Conditions</Text>
        <Text style={styles.body}>
          1. The provider is required to maintain a quality management system.{"\n"}
          2. The provider must notify merSETA of any changes to its physical location.{"\n"}
          3. Re-accreditation must be applied for at least 6 months prior to expiry.
        </Text>
      </View>

      {/* Signatures */}
      <View style={styles.signatureSection}>
        <View style={styles.signatureBox}>
          <Text style={{ fontSize: 10 }}>Senior Manager: Quality Assurance</Text>
        </View>
        <View style={styles.signatureBox}>
          <Text style={{ fontSize: 10 }}>Date</Text>
        </View>
      </View>

      {/* Footer */}
      <View style={styles.footer}>
        <Text>merSETA Head Office: 95 7th Avenue, Melville, Johannesburg, 2109</Text>
        <Text>www.merseta.org.za | Tel: 010 219 3000</Text>
      </View>
    </Page>
  </Document>
);
