let userScore = 0;
let computerScore = 0;

const USER_SCORE_SPAN = document.getElementById('user-scored');
const COMPUTER_SCORE_SPAN = document.getElementById('computer-scored');
const SCORE_BOARD_DIV = document.getElementById('score-board');
const RESULT_DIV = document.getElementById('result');
const ROCK_CHOICE_DIV = document.getElementById('rock');
const PAPER_CHOICE_DIV = document.getElementById('paper');
const SCISSORS_CHOICE_DIV = document.getElementById('scissors');

const getComputerRandomChoice = () => {
  const choices = ['rock', 'paper', 'scissors'];
  const randomChoiceIndex = Math.floor(Math.random() * 3);
  return choices[randomChoiceIndex];
};

const convertCase = anythingIwant => {
  if (anythingIwant === 'paper') return 'Paper';
  if (anythingIwant === 'scissors') return 'Scissors';
  return 'Rock';
};

const win = (user, computer) => {
  userScore++;
  USER_SCORE_SPAN.innerHTML = userScore;
  const userName = ' (user)'.fontsize(3).sup();
  const compName = ' (comp)'.fontsize(3).sup();
  RESULT_DIV.innerHTML = `<p>${convertCase(
    user,
  )}${userName} beats ${convertCase(computer)}${compName}. You win!</p>`;
  const roundStatus = document.getElementById(user);
  roundStatus.classList.add('winningStyles');
  setTimeout(() => roundStatus.classList.remove('winningStyles'), 300);
};

const loses = (user, computer) => {
  computerScore++;
  COMPUTER_SCORE_SPAN.innerHTML = computerScore;
  const userName = ' (user)'.fontsize(3).sup();
  const compName = ' (comp)'.fontsize(3).sup();
  RESULT_DIV.innerHTML = `<p>${convertCase(
    computer,
  )}${compName} beats ${convertCase(user)}${userName}. You lose!</p>`;
  const roundStatus = document.getElementById(user);
  roundStatus.classList.add('losingStyles');
  setTimeout(() => roundStatus.classList.remove('losingStyles'), 300);
};

const draw = (user, computer) => {
  const userName = ' (user)'.fontsize(3).sup();
  const compName = ' (comp)'.fontsize(3).sup();
  RESULT_DIV.innerHTML = `<p>It was a draw! You both chose ${convertCase(
    user,
  )}</p>`;
  const roundStatus = document.getElementById(user);
  roundStatus.classList.add('drawStyles');
  setTimeout(() => roundStatus.classList.remove('drawStyles'), 300);
};

const engine = userChoice => {
  const computerChoice = getComputerRandomChoice();
  const choiceComposite = `user:${userChoice} | comp:${computerChoice}`;
  switch (choiceComposite) {
    case 'user:paper | comp:rock':
    case 'user:rock | comp:scissors':
    case 'user:scissors | comp:paper':
      win(userChoice, computerChoice);
      break;
    case 'user:rock | comp:paper':
    case 'user:scissors | comp:rock':
    case 'user:paper | comp:scissors':
      loses(userChoice, computerChoice);
      break;
    case 'user:rock | comp:rock':
    case 'user:paper | comp:paper':
    case 'user:scissors | comp:scissors':
      draw(userChoice, computerChoice);
      break;
  }
};

const main = () => {
  ROCK_CHOICE_DIV.addEventListener('click', () => engine('rock'));
  PAPER_CHOICE_DIV.addEventListener('click', () => engine('paper'));
  SCISSORS_CHOICE_DIV.addEventListener('click', () => engine('scissors'));
};

main();
