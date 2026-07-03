export default function AnalyticsCards({ data }) {
  const cards = [
    { label: "Total Claims",   value: data.total_claims,                    color: "#3498db" },
    { label: "Total Amount",   value: `$${data.total_amount?.toLocaleString()}`, color: "#2ecc71" },
    { label: "Avg Amount",     value: `$${data.average_amount?.toLocaleString()}`, color: "#9b59b6" },
    { label: "Flagged",        value: data.flagged_count,                   color: "#e74c3c" },
    { label: "Approved",       value: data.approved_count,                  color: "#27ae60" },
    { label: "Pending",        value: data.pending_count,                   color: "#f39c12" },
  ];

  return (
    <div className="analytics-grid">
      {cards.map(({ label, value, color }) => (
        <div className="analytics-card" key={label} style={{ borderTop: `4px solid ${color}` }}>
          <div className="card-value" style={{ color }}>{value}</div>
          <div className="card-label">{label}</div>
        </div>
      ))}
    </div>
  );
}
