export default function ClaimsTable({ claims, onDelete }) {
  if (!claims.length) return <p className="empty">No claims found.</p>;

  return (
    <div className="table-wrapper">
      <table className="claims-table">
        <thead>
          <tr>
            <th>Claim ID</th>
            <th>Patient</th>
            <th>Medication</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Flag</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {claims.map(claim => (
            <tr key={claim.claim_id} className={claim.flagged ? "row-flagged" : ""}>
              <td><code>{claim.claim_id}</code></td>
              <td>{claim.patient}</td>
              <td>{claim.medication}</td>
              <td>${claim.amount?.toLocaleString()}</td>
              <td>
                <span className={`badge badge-${claim.status}`}>
                  {claim.status}
                </span>
              </td>
              <td>{claim.flagged ? "🚨" : "✅"}</td>
              <td>
                <button className="btn-delete" onClick={() => onDelete(claim.claim_id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
