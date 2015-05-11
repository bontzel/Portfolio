function isFloat(str) {
    return /^(\+|\-)?\d+\.\d*$/.test(str);
}

function isInt(str) {
    return /^(\+|\-)?\d*$/.test(str);
}

function isNum(str) {
    if (
        isFloat(str) ||
        isInt(str)
    )
        return true;
    else {
        return false;
    }
}

function remTermAndOp() {
    $('.term').detach();
    $('.op').detach();
    dis.css("margin-top", "200px");

}

function solve(term2, opNum) {

    t1 = parseFloat($('.term').html());
    t2 = parseFloat(term2);
    op = $('.op').html();

    remTermAndOp();

    switch (op) {
        case '+':
            result = (t1 + t2).toString();
            break;
        case '-':
            result = (t1 - t2).toString();
            break;
        case '/':
            result = (t1 / t2).toString();
            break;
        case '*':
            result = (t1 * t2).toString();
            break;
    }

    historySize = $('.history ul li');
    opsList = $('.history ul');
    operation = '<li>' + opNum + ': ' + t1 + ' ' + op + ' ' + t2 + ' = ' + result + '</li>';

    if (historySize.length < 5)
        opsList.prepend(operation);
    else {
        $('.history ul li:last-child').remove();
        opsList.prepend(operation);
    }

    return result;
}

function drawTermAndOp(term, op) {

    dis = $('.display');
    dis.before('<div class="term">' + term + '</div>');
    dis.css("margin-top", "1px");

    dis.before('<div class="op">' + op + '</div>');

    dis.val("");
}


var workyStuff = function() {
    dis = $('.display');
    var mem = 0;
    var solvable;
    var opNum = 0;

    dis.on('keypress', function(e) {
        var charCode = (e.which) ? e.which : event.keyCode;
        c = String.fromCharCode(charCode);
        isOp = /^[+\-\*\/]$/.test(c);
        isSign = /^[+\-]$/.test(c);
        currentInput = dis.val() + c;



        if (solvable == 1 && charCode == 13) {
            opNum++;
            dis.val(solve(currentInput, opNum));
            solvable = 0;
            return true;
        } else if (isOp && !/^[+\-]$/.test(dis.val()) && dis.val().length != 0 && !solvable) {
            drawTermAndOp(dis.val(), c);
            solvable = 1;
            return false;
        } else if (isSign && dis.val().length == 0) {
            return true;
        }


        if (isNum(currentInput)) {
            return true;
        } else
            return false;
    });

    $('.btn').on('click', function(e) {
        var c = $(event.target).html();
        var currentInput = dis.val() + c;

        isOp = /^[+\-\*\/]$/.test(c);


        switch (c) {
            case "C":
                dis.val("");
                dis.focus();
                break;
            case "MC":
                mem = 0;
                break;
            case "MS":
                mem = parseFloat(dis.val());
                break;
            case "MR":
                dis.val(mem);
                break;
            case "M+":
                mem += dis.val();
                break;
            case "M-":
                mem -= dis.val();
                break;
        }

        if (solvable == 1 && c === "=") {
            dis.val(solve(currentInput, opNum));
            solvable = 0;
            return true;
        } else if (isOp && !/^[+\-]$/.test(dis.val()) && dis.val().length != 0) {
            drawTermAndOp(dis.val(), c);
            solvable = 1;
            return false;
        } else if (isOp && dis.val().length == 0) {
            dis.val(currentInput);
        }


        if (isNum(currentInput)) {
            dis.val(currentInput);
        } else
            return false;

    });



	$('.convert_btn').on('click', function() {
		$('body').prepend('<div class="convert"><label> From: </label><input type="text" class="from"><label> To: </label><input type="text" class="to"><button class="doConvert">Convert</button></div>');

	});

	$('body').on('click','.convert button', function() {
	    var from = $('.from').val();
	    var to = $('.to').val();
	    var amount = dis.val();

		var output = $.ajax({
		    url: 'https://currency-exchange.p.mashape.com/exchange', 
		    type: 'GET', 
		    data: { from: from,
		    		to: to }, 
		    datatype: 'json',
		    success: function(data) {
		    		dis.val(data * parseFloat(amount));
		        },
		    error: function(err) { alert(err); },
		    beforeSend: function(xhr) {
		    	xhr.setRequestHeader("X-Mashape-Authorization", "4AS2JvjrVLmshBUqZylZQ8WmZPFip1hyMX5jsnpyQQjHGZdUBd"); // Enter here your Mashape key
		    }
		});
  
	});
}

workyStuff();