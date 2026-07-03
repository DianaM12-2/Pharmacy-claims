import { useState, useEffect } from "react";
import ClaimsTable from "./components/ClaimsTable";
import AnalyticsCards from "./components/AnalyticsCards";
import ClaimForm from "./components/ClaimForm";
import FilterBar from "./components/FilterBar";
import { fetchClaims, fetchAnalytics, createClaim, deleteClaim } from "./services/api";
import "./styles/App.css";

export default function App() {
  const [claims, setClaims]       = useState([]);
  const [analytics, setAnalytics] = useState(null);
  const [filter, setFilter]       = useState("all");
  const [loading, setLoading]     = useState(true);
  const [error, setError]         = useState(null);
  const [showForm, setShowForm]   = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  async function loadData() {
    try {
      setLoading(true);
      const [claimsData, analyticsData] = await Promise.all([
        fetchClaims(),
        fetchAnalytics(),
      ]);
      setClaims(claimsData);
      setAnalytics(analyticsData);
    } catch (err) {
      setError("Failed to load data. Make sure the backend is running.");
    } finally {
      setLoading(false);
    }
  }

  async function handleCreate(claimData) {
    try {
      const newClaim = await createClaim(claimData);
      setClaims(prev => [newClaim, ...prev]);
      setShowForm(false);
      loadData(); // refresh analytics
    } catch (err) {
      alert("Error creating claim: " + err.message);
    }
  }

  async function handleDelete(claimId) {
    if (!window.confirm(`Delete claim ${claimId}?`)) return;
    try {
      await deleteClaim(claimId);
      setClaims(prev => prev.filter(c => c.claim_id !== claimId));
      loadData();
    } catch (err) {
      alert("Error deleting claim: " + err.message);
    }
  }

  const filteredClaims = claims.filter(c => {
    if (filter === "all")     return true;
    if (filter === "flagged") return c.flagged;
    return c.status === filter;
  });

  if (loading) return <div className="loading">Loading claims...</div>;
  if (error)   return <div className="error">{error}</div>;

  return (
    <div className="app">
      <header className="app-header">
        <h1>Pharmacy Claims Dashboard</h1>
        <p className="subtitle">Real-time claims monitoring and fraud detection</p>
      </header>

      <main className="app-main">
        {analytics && <AnalyticsCards data={analytics} />}

        <div className="table-section">
          <div className="table-header">
            <FilterBar value={filter} onChange={setFilter} />
            <button className="btn-primary" onClick={() => setShowForm(!showForm)}>
              {showForm ? "Cancel" : "+ New Claim"}
            </button>
          </div>

          {showForm && <ClaimForm onSubmit={handleCreate} />}

          <ClaimsTable
            claims={filteredClaims}
            onDelete={handleDelete}
          />
        </div>
      </main>
    </div>
  );
}
