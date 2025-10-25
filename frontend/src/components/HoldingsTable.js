import React, { useState, useEffect } from 'react';

const HoldingsTable = ({ refreshTrigger }) => {
  const [holdings, setHoldings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [expandedRows, setExpandedRows] = useState([]);

  useEffect(() => {
    const fetchHoldings = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await fetch('/api/portfolio/holdings');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setHoldings(data);
      } catch (e) {
        setError('Failed to fetch holdings.');
        console.error('Error fetching holdings:', e);
      } finally {
        setLoading(false);
      }
    };

    fetchHoldings();
  }, [refreshTrigger]);

  const groupedHoldings = new Map();
  holdings.forEach(holding => {
    const { symbol } = holding;
    if (!groupedHoldings.has(symbol)) {
      groupedHoldings.set(symbol, {
        holdings: [],
        totalQuantity: 0,
        totalValue: 0,
        totalChangeValue: 0,
      });
    }
    const group = groupedHoldings.get(symbol);
    group.holdings.push(holding);
    group.totalQuantity += holding.quantity;
    group.totalValue += holding.price * holding.quantity;
    group.totalChangeValue += holding.totalChangeValue;
  });

  groupedHoldings.forEach(group => {
    group.weightedAvgPrice = group.totalValue / group.totalQuantity;
    const totalOriginalValue = group.holdings.reduce((acc, h) => acc + (h.purchasePrice || 0) * h.quantity, 0);
    group.totalChangePercent = totalOriginalValue > 0 ? (group.totalChangeValue / totalOriginalValue) * 100 : 0;
  });

  const toggleRow = (symbol) => {
    if (expandedRows.includes(symbol)) {
      setExpandedRows(expandedRows.filter(s => s !== symbol));
    } else {
      setExpandedRows([...expandedRows, symbol]);
    }
  };

  if (loading) return <div className="text-center dark:text-white">Loading holdings...</div>;
  if (error) return <div className="text-center text-red-500 dark:text-red-400">Error: {error}</div>;

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6">
      <h2 className="text-xl font-bold text-gray-800 dark:text-white mb-4">Holdings</h2>

      {/* Desktop Table */}
      <div className="hidden md:block">
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-200 dark:border-gray-700 text-left text-sm font-medium text-gray-500 dark:text-gray-400">
              <th className="p-3">Name</th>
              <th className="p-3">Symbol</th>
              <th className="p-3 text-right">Quantity</th>
              <th className="p-3 text-right">Avg. Price</th>
              <th className="p-3 text-right">Total Change</th>
              <th className="p-3 text-right">Total Value</th>
            </tr>
          </thead>
          <tbody>
            {Array.from(groupedHoldings.values()).map((group, index) => (
              <React.Fragment key={`${group.holdings[0].symbol}-${index}`}>
                <tr className="border-b border-gray-200 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-700">
                  <td className="p-3 flex items-center">
                    <button onClick={() => toggleRow(group.holdings[0].symbol)} className="p-1 rounded-full hover:bg-gray-200 dark:hover:bg-gray-600 mr-2">
                      <svg className={`w-4 h-4 transform transition-transform ${expandedRows.includes(group.holdings[0].symbol) ? 'rotate-180' : ''}`} fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7"></path></svg>
                    </button>
                    <img src={group.holdings[0].logoUrl} alt={group.holdings[0].name} className="h-8 w-8 mr-4 rounded-full"/>
                    <span className="font-medium text-gray-800 dark:text-white">{group.holdings[0].name}</span>
                  </td>
                  <td className="p-3 text-gray-500 dark:text-gray-400">{group.holdings[0].symbol}</td>
                  <td className="p-3 text-right font-medium text-gray-800 dark:text-white">{group.totalQuantity}</td>
                  <td className="p-3 text-right font-medium text-gray-800 dark:text-white">€{(group.weightedAvgPrice || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</td>
                  <td className={`p-3 text-right font-medium ${group.totalChangeValue >= 0 ? 'text-green-500' : 'text-red-500'}`}>
                    {group.totalChangeValue >= 0 ? '+' : ''}{(group.totalChangeValue || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€ ({(group.totalChangePercent || 0).toFixed(2)}%)
                  </td>
                  <td className={`p-3 text-right font-medium ${group.totalValue >= 0 ? 'text-green-500' : 'text-red-500'}`}>
                    €{(group.totalValue || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
                  </td>
                </tr>
                {expandedRows.includes(group.holdings[0].symbol) && group.holdings.map((h) => (
                  <tr key={h.id} className="bg-gray-100 dark:bg-gray-900 border-b border-gray-200 dark:border-gray-700">
                    <td className="p-3 pl-12 text-sm text-gray-500 dark:text-gray-400" colSpan="2">Purchased on {h.purchaseDate && !isNaN(new Date(h.purchaseDate).getTime()) ? new Date(h.purchaseDate).toLocaleDateString() : 'Unknown Date'}</td>
                    <td className="p-3 text-right text-sm font-medium text-gray-800 dark:text-white">{h.quantity}</td>
                    <td className="p-3 text-right text-sm font-medium text-gray-800 dark:text-white">€{(h.purchasePrice || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</td>
                    <td className="p-3" colSpan="2"></td>
                  </tr>
                ))}
              </React.Fragment>
            ))}
          </tbody>
        </table>
      </div>

      {/* Mobile Cards */}
      <div className="md:hidden">
        {Array.from(groupedHoldings.values()).map((group, index) => (
          <div key={`${group.holdings[0].symbol}-${index}`} className="bg-gray-50 dark:bg-gray-700 rounded-lg p-4 mb-4 shadow">
            <div onClick={() => toggleRow(group.holdings[0].symbol)} className="cursor-pointer flex justify-between items-center mb-4">
              <div className="flex items-center">
                <button onClick={() => toggleRow(group.holdings[0].symbol)} className="p-1 rounded-full hover:bg-gray-200 dark:hover:bg-gray-600 mr-2">
                  <svg className={`w-5 h-5 transform transition-transform ${expandedRows.includes(group.holdings[0].symbol) ? 'rotate-180' : ''}`} fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7"></path></svg>
                </button>
                <img src={group.holdings[0].logoUrl} alt={group.holdings[0].name} className="h-10 w-10 mr-4 rounded-full"/>
                <div>
                  <p className="font-bold text-gray-800 dark:text-white">{group.holdings[0].name}</p>
                  <p className="text-sm text-gray-500 dark:text-gray-400">{group.holdings[0].symbol}</p>
                </div>
              </div>
              <div className="text-right">
                <p className="font-bold text-lg text-gray-800 dark:text-white">€{(group.weightedAvgPrice || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
                <p className="text-sm text-gray-500 dark:text-gray-400">{group.totalQuantity} shares</p>
              </div>
            </div>
            {expandedRows.includes(group.holdings[0].symbol) && (
              <div className="border-t border-gray-200 dark:border-gray-600 pt-4">
                {group.holdings.map(h => (
                  <div key={h.id} className="flex justify-between items-center mb-2 text-sm">
                    <span className="text-gray-500 dark:text-gray-400">Purchased on {h.purchaseDate && !isNaN(new Date(h.purchaseDate).getTime()) ? new Date(h.purchaseDate).toLocaleDateString() : 'Unknown Date'}</span>
                    <div className="text-right">
                      <p className="font-medium text-gray-800 dark:text-white">{h.quantity} shares at €{(h.purchasePrice || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
                    </div>
                  </div>
                ))}
              </div>
            )}
            <div className="flex justify-between mt-4">
              <div>
                <p className="text-sm text-gray-500 dark:text-gray-400">Total Change</p>
                <p className={`font-medium ${group.totalChangeValue >= 0 ? 'text-green-500' : 'text-red-500'}`}>
                  {group.totalChangeValue >= 0 ? '+' : ''}{(group.totalChangeValue || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€ ({(group.totalChangePercent || 0).toFixed(2)}%)
                </p>
              </div>
              <div className="text-right">
                <p className="text-sm text-gray-500 dark:text-gray-400">Total Value</p>
                <p className="font-medium text-gray-800 dark:text-white">€{(group.totalValue || 0).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default HoldingsTable;