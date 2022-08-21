/* All Matrix processing is done before transposing into render format. */

const NUM_ROW = 6; 
const NUM_COL = 7;
const AGENT   = '2'
const CLIENT  = '1'

const checkHorizontal = (board, client) => {
    let winSequence = 0;
    for (let c = 0; c < NUM_COL - 3; c++) { // for each column
		for (let r = 0; r < NUM_ROW; r++) { // each row
			for (let i = 0; i < 4; i++) { // recall you need 4 to win
				if (board[r][c + i] == client) { // if not all pieces match
					winSequence++; // add sequence count
				}
				if (winSequence == 4) { return true; } // if 4 in row
			}
			winSequence = 0; // reset counter
		}
	}
    return false
}

const checkVertical = (board, client) => {
	let winSequence = 0;
	for (let c = 0; c < NUM_COL; c++) {
		for (let r = 0; r < NUM_ROW - 3; r++) {
			for (let i = 0; i < 4; i++) {
				if (board[r + i][c] == client) {
					winSequence++;
				}
				if (winSequence == 4) { return true; }
			}
			winSequence = 0;
		}
	}
	return false;
}

// Two sweeps for different directions.
const checkDiagonal = (board, client) => {
	let winSequence = 0;
	// First sweep
	for (let c = 0; c < NUM_COL - 3; c++) {
		for (let r = 3; r < NUM_ROW; r++) {
			for (let i = 0; i < 4; i++) {
				if (board[r - i][c + i] == client) {
					winSequence++;
				}
				if (winSequence == 4) { return true; }
			}
			winSequence = 0;
		}
	}
	// Second sweep
	for (let c = 0; c < NUM_COL - 3; c++) {
		for (let r = 0; r < NUM_ROW - 3; r++) {
			for (let i = 0; i < 4; i++) {
				if (board[r + i][c + i] == client) {
					winSequence++;
				}
				if (winSequence == 4) { return true; }
			}
			winSequence = 0;
		}
	}
	return false; 
}

// TODO format checker functions to make 1 pass and check both.
const checkWinner = (board) => {
	const runCheckers = (player) => {
		const checks = checkHorizontal(board, player) 
					|| checkVertical(board, player) 
					|| checkDiagonal(board, player)
		
		return checks ? player : null
	}
	return runCheckers(AGENT) | runCheckers(CLIENT) | null
}

export default checkWinner;
