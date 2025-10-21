import React, { useState, useEffect } from 'react';
import { AreaChart, Area, XAxis, YAxis, Tooltip, ResponsiveContainer } from 'recharts';
import useDarkMode from '../hooks/useDarkMode';

const ranges = ['1M', '3M', '6M', '1Y', '5Y', 'MAX'];

const CustomTooltip = ({ active, payload, label }) => {
  if (active && payload && payload.length) {
    return (
      <div className="bg-white dark:bg-gray-700 p-2 shadow-md rounded-md">
        <p className="font-bold text-gray-800 dark:text-white">{label}</p>
        <p className="text-indigo-500 dark:text-indigo-400">{`Value: €${payload[0].value.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`}</p>
      </div>
    );
  }

  return null;
};

const PortfolioChart = ({ refreshTrigger }) => {
  const [range, setRange] = useState('1Y');
  const [chartData, setChartData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [colorTheme] = useDarkMode();

  useEffect(() => {
    const fetchPortfolioHistory = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await fetch(`/api/portfolio/history?range=${range}`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setChartData(data.map(item => ({ name: item.date, value: item.value })));
      } catch (e) {
        setError('Failed to fetch portfolio history.');
        console.error('Error fetching portfolio history:', e);
      } finally {
        setLoading(false);
      }
    };

    fetchPortfolioHistory();
  }, [range, refreshTrigger]);

  if (loading) return <div className="text-center dark:text-white">Loading chart data...</div>;
  if (error) return <div className="text-center text-red-500 dark:text-red-400">Error: {error}</div>;

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6 mb-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-gray-800 dark:text-white">Portfolio Performance</h2>
        <div className="flex space-x-1 bg-gray-100 dark:bg-gray-700 p-1 rounded-lg">
          {ranges.map((r) => (
            <button
              key={r}
              className={`px-3 py-1 text-sm font-medium rounded-md transition-colors ${range === r ? 'bg-white dark:bg-gray-600 text-gray-800 dark:text-white shadow' : 'text-gray-500 dark:text-gray-400 hover:bg-gray-200 dark:hover:bg-gray-600'}`}
              onClick={() => setRange(r)}
            >
              {r}
            </button>
          ))}
        </div>
      </div>
      <ResponsiveContainer width="100%" height={300}>
        <AreaChart data={chartData} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
          <defs>
            <linearGradient id="colorValue" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor={colorTheme === 'light' ? '#8884d8' : '#6366F1'} stopOpacity={0.8}/>
              <stop offset="95%" stopColor={colorTheme === 'light' ? '#8884d8' : '#6366F1'} stopOpacity={0}/>
            </linearGradient>
          </defs>
          <XAxis dataKey="name" tick={{ fill: colorTheme === 'light' ? '#6B7280' : '#9CA3AF' }} />
          <YAxis tick={{ fill: colorTheme === 'light' ? '#6B7280' : '#9CA3AF' }} tickFormatter={(value) => `€${value.toLocaleString('de-DE')}`} />
          <Tooltip content={<CustomTooltip />} />
          <Area type="monotone" dataKey="value" stroke={colorTheme === 'light' ? '#8884d8' : '#6366F1'} strokeWidth={2} fill="url(#colorValue)" />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  );
};

export default PortfolioChart;