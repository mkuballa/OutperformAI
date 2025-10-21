import React from 'react';
import useDarkMode from '../hooks/useDarkMode';
import { SunIcon, MoonIcon } from '@heroicons/react/24/solid';

const TrendingUpIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
    <path fillRule="evenodd" d="M12 7a1 1 0 11-2 0 1 1 0 012 0zm-2 2a1 1 0 100 2h2a1 1 0 100-2h-2z" clipRule="evenodd" />
    <path d="M4 3a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V5a2 2 0 00-2-2H4zm12 12H4l4-4 3 3 5-5v7z" />
  </svg>
);

const TrendingDownIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
    <path fillRule="evenodd" d="M12 13a1 1 0 100-2 1 1 0 000 2z" clipRule="evenodd" />
    <path d="M4 3a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V5a2 2 0 00-2-2H4zm12 12H4l4-4 3 3 5-5v7z" />
  </svg>
);

const Header = () => {
  const [colorTheme, setTheme] = useDarkMode();

  const portfolio = {
    totalValue: 123456.78,
    dailyChange: { value: 1234.56, percent: 1.01 },
    totalChange: { value: 23456.78, percent: 23.45 },
  };

  const dailyChangePositive = portfolio.dailyChange.value >= 0;
  const totalChangePositive = portfolio.totalChange.value >= 0;

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6 mb-6">
      <div className="flex justify-between items-start">
        <div className="flex items-center">
          <div>
            <h1 className="text-3xl font-bold text-gray-800 dark:text-white">My Portfolio</h1>
            <p className="text-gray-500 dark:text-gray-400">Welcome back!</p>
          </div>
        </div>
        <div className="flex items-center">
          <button onClick={() => setTheme(colorTheme)} className="w-10 h-10 bg-gray-200 dark:bg-gray-700 rounded-full flex items-center justify-center mr-6">
            {colorTheme === 'light' ? <SunIcon className="w-6 h-6 text-yellow-500" /> : <MoonIcon className="w-6 h-6 text-white" />}
          </button>
          <div className="text-right">
            <p className="text-sm text-gray-500 dark:text-gray-400">Total Portfolio Value</p>
            <p className="text-4xl font-bold text-gray-800 dark:text-white mb-2">€{portfolio.totalValue.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
            <div className="flex justify-end space-x-6">
              <div>
                <p className="text-sm text-gray-500 dark:text-gray-400">Daily Change</p>
                <div className={`flex items-center justify-end ${dailyChangePositive ? 'text-green-500' : 'text-red-500'}`}>
                  {dailyChangePositive ? <TrendingUpIcon /> : <TrendingDownIcon />}
                  <span className="font-bold">{dailyChangePositive ? '+' : ''}{portfolio.dailyChange.value.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€</span>
                  <span className="text-sm ml-2">({portfolio.dailyChange.percent.toFixed(2)}%)</span>
                </div>
              </div>
              <div>
                <p className="text-sm text-gray-500 dark:text-gray-400">Total Change</p>
                <div className={`flex items-center justify-end ${totalChangePositive ? 'text-green-500' : 'text-red-500'}`}>
                  {totalChangePositive ? <TrendingUpIcon /> : <TrendingDownIcon />}
                  <span className="font-bold">{totalChangePositive ? '+' : ''}{portfolio.totalChange.value.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}€</span>
                  <span className="text-sm ml-2">({portfolio.totalChange.percent.toFixed(2)}%)</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Header;