import React, { useState, useEffect } from 'react';

const HoldingsTable = ({ refreshTrigger }) => {
  const [holdings, setHoldings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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
              <th className="p-3 text-right">Current Price</th>
              <th className="p-3 text-right">Daily Change</th>
              <th className="p-3 text-3xl text-right">Total Change</th>
            </tr>
          </thead>
          <tbody>
            {holdings.map((holding) => (
              <tr key={holding.symbol} className="border-b border-gray-200 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-700">
                <td className="p-3 flex items-center">
                  <img src={holding.logoUrl} alt={holding.name} className="h-8 w-8 mr-4 rounded-full"/>
                  <span className="font-medium text-gray-800 dark:text-white">{holding.name}</span>
                </td>
                <td className="p-3 text-gray-500 dark:text-gray-400">{holding.symbol}</td>
                <td className="p-3 text-right font-medium text-gray-800 dark:text-white">{holding.quantity}</td>
                <td className="p-3 text-right font-medium text-gray-800 dark:text-white">€{holding.price.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</td>
                <td className={`p-3 text-right font-medium ${holding.dailyChangeValue >= 0 ? 'text-green-500' : 'text-red-500'}`}>
                  {holding.dailyChangeValue >= 0 ? '+' : ''}{holding.dailyChangeValue.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€ ({holding.dailyChangePercent.toFixed(2)}%)
                </td>
                <td className={`p-3 text-right font-medium ${holding.totalChangeValue >= 0 ? 'text-green-500' : 'text-red-500'}`}>
                  {holding.totalChangeValue >= 0 ? '+' : ''}{holding.totalChangeValue.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€ ({holding.totalChangePercent.toFixed(2)}%)
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Mobile Cards */}
      <div className="md:hidden">
        {holdings.map((holding) => (
          <div key={holding.symbol} className="bg-gray-50 dark:bg-gray-700 rounded-lg p-4 mb-4 shadow">
            <div className="flex justify-between items-center mb-4">
              <div className="flex items-center">
                <img src={holding.logoUrl} alt={holding.name} className="h-10 w-10 mr-4 rounded-full"/>
                <div>
                  <p className="font-bold text-gray-800 dark:text-white">{holding.name}</p>
                  <p className="text-sm text-gray-500 dark:text-gray-400">{holding.symbol}</p>
                </div>
              </div>
              <div className="text-right">
                <p className="font-bold text-lg text-gray-800 dark:text-white">€{holding.price.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
                <p className="text-sm text-gray-500 dark:text-gray-400">{holding.quantity} shares</p>
              </div>
            </div>
            <div className="flex justify-between">
              <div>
                <p className="text-sm text-gray-500 dark:text-gray-400">Daily Change</p>
                <p className={`font-medium ${holding.dailyChangeValue >= 0 ? 'text-green-500' : 'text-red-500'}`}>
                  {holding.dailyChangeValue >= 0 ? '+' : ''}{holding.dailyChangeValue.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€ ({holding.dailyChangePercent.toFixed(2)}%)
                </p>
              </div>
              <div className="text-right">
                <p className="text-sm text-gray-500 dark:text-gray-400">Total Change</p>
                <p className={`font-medium ${holding.totalChangeValue >= 0 ? 'text-green-500' : 'text-red-500'}`}>
                  {holding.totalChangeValue >= 0 ? '+' : ''}{holding.totalChangeValue.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€ ({holding.totalChangePercent.toFixed(2)}%)
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default HoldingsTable;