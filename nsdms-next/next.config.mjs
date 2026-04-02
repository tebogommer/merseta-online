/** @type {import('next').NextConfig} */
const nextConfig = {
  // Disabling strict mode/lints for the rapid early MVP phase as per user rules
  typescript: {
    ignoreBuildErrors: true,
  }
};

export default nextConfig;
