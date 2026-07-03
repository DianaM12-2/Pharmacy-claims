const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:5000/api/v1";

async function request(path, options = {}) {
  const res = await fetch(`${BASE_URL}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({ error: res.statusText }));
    throw new Error(err.error || "Request failed");
  }
  return res.status === 204 ? null : res.json();
}

export const fetchClaims    = ()           => request("/claims");
export const fetchAnalytics = ()           => request("/claims/analytics");
export const createClaim    = (data)       => request("/claims", { method: "POST", body: JSON.stringify(data) });
export const deleteClaim    = (claimId)    => request(`/claims/${claimId}`, { method: "DELETE" });
export const getFlagged     = ()           => request("/claims/flagged");
