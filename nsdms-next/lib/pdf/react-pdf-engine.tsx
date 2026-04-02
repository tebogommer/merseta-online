import { renderToStream } from "@react-pdf/renderer";
import { AccreditationCertificateTemplate, AccreditationTemplateProps } from "./templates/accreditation-certificate";
import { Readable } from "stream";

/**
 * ReactPDF Engine
 * High-performance, memory-efficient PDF generation using React components.
 * This should be used for all standard MerSETA compliance documents.
 */

export async function generateAccreditationPDF(data: AccreditationTemplateProps): Promise<Buffer> {
  const stream = await renderToStream(AccreditationCertificateTemplate(data));
  
  return new Promise((resolve, reject) => {
    const chunks: any[] = [];
    stream.on("data", (chunk) => chunks.push(chunk));
    stream.on("end", () => resolve(Buffer.concat(chunks)));
    stream.on("error", (err) => reject(err));
  });
}
