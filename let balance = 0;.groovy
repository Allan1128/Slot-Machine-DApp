let balance = 0;

const SYMBOLS = ["🍒", "🍋", "🍊", "🍉", "⭐", "🔔", "🍇"];
const MODES = {
    'a': { name: '简单模式', reels: 3, cost: 300, reward: 5000, win_condition: r => new Set(r).size === 1 },
    'b': { name: '中等模式', reels: 4, cost: 450, reward: 8000, win_condition: r => new Set(r).size === 1 },
    'c': { name: '困难模式', reels: 6, cost: 500, reward: 10000, win_condition: r => new Set(r).size === 1 },
    'd': { name: '地狱模式', reels: 12, cost: 800, reward: 1000000, win_condition: r => new Set(r).size === 1 && Math.random() < 0.0001 }
};

function updateBalance() {
    document.getElementById('balance').innerText = `当前余额: $${balance}`;
}

function spinReels(numReels) {
    return Array.from({ length: numReels }, () => SYMBOLS[Math.floor(Math.random() * SYMBOLS.length)]);
}

function playGame(mode) {
    if (balance < mode.cost) {
        alert("余额不足，请充值。");
        return;
    }

    balance -= mode.cost;
    updateBalance();

    const result = spinReels(mode.reels);
    document.getElementById('result').innerText = `结果: ${result.join(' | ')}`;

    if (mode.win_condition(result)) {
        balance += mode.reward;
        updateBalance();
        alert(`🎆🎇 恭喜，你赢了 $${mode.reward}! 🎇🎆`);
    } else {
        alert("很遗憾，你输了。");
    }
}

function deposit() {
    const amount = parseInt(prompt("输入充值金额: "));
    if (isNaN(amount)) {
        alert("错误: 请输入数字");
        return;
    }
    balance += amount;
    updateBalance();
    alert(`充值成功，当前余额: $${balance}`);
}

function withdraw() {
    if (balance === 0) {
        alert("余额不足，无法提现。");
        return;
    }
    const amount = parseInt(prompt("输入提现金额: "));
    if (isNaN(amount)) {
        alert("错误: 请输入数字");
        return;
    }
    if (amount <= balance) {
        balance -= amount;
        updateBalance();
        alert(`提现成功，当前余额: $${balance}`);
    } else {
        alert("余额不足，无法提现。");
    }
}

function play() {
    const modeChoice = prompt("请选择模式 (A/B/C/D): ").toLowerCase();
    if (!MODES.hasOwnProperty(modeChoice)) {
        alert("无效的选择，退出游戏。");
        return;
    }

    const mode = MODES[modeChoice];
    alert(`你选择了${mode.name}，每次游戏费用为$${mode.cost}。`);
    playGame(mode);
}

updateBalance();