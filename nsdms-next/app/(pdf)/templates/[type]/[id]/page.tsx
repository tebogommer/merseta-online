import React from 'react';

// Example Route: /templates/certificate/123
export default async function CertificateTemplatePage({ params }: { params: { id: string, type: string } }) {
  // In a real scenario, you'd fetch the document data from Prisma based on `params.id`
  // const data = await prisma.certificates.findUnique({ where: { id: params.id }});

  return (
    <div className="w-[210mm] h-[297mm] mx-auto bg-white p-12 text-slate-900 border-[10px] border-double border-slate-300 relative">
      <div className="flex flex-col items-center justify-center h-full text-center space-y-8">
        
        <div className="absolute top-12 left-12 text-left">
          <p className="font-mono text-sm text-slate-400">REF: {params.id}</p>
          <p className="font-mono text-sm text-slate-400">DATE: {new Date().toLocaleDateString()}</p>
        </div>

        <div>
          <h1 className="text-4xl font-serif font-bold text-slate-800 uppercase tracking-widest pt-12">
            Certificate of Compliance
          </h1>
          <div className="h-1 w-24 bg-primary mx-auto mt-6 rounded">&nbsp;</div>
        </div>

        <div className="py-12 space-y-4">
          <p className="text-lg text-slate-600 italic">This is to certify that</p>
          <h2 className="text-3xl font-bold text-slate-800">Acme Corporation Ltd.</h2>
          <p className="text-lg text-slate-600 max-w-md mx-auto pt-4 leading-relaxed">
            Has successfully fulfilled the mandatory compliance requirements for the National Skills Development program standards.
          </p>
        </div>

        <div className="grid grid-cols-2 gap-24 w-full mt-24 pt-12 border-t border-slate-200">
          <div>
            <div className="border-b border-slate-400 pb-2 mb-2 italic">John Smith</div>
            <p className="text-sm font-semibold uppercase tracking-wider text-slate-500">Chief Auditor</p>
          </div>
          <div>
            <div className="border-b border-slate-400 pb-2 mb-2 italic">Jane Doe</div>
            <p className="text-sm font-semibold uppercase tracking-wider text-slate-500">Program Director</p>
          </div>
        </div>

      </div>
    </div>
  );
}
