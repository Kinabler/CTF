use std::io::{self, Read, BufRead};

fn main() {
    let input = loop {
        match get_line() {
            Ok(my_str) => break my_str,
            Err(_) => {
                print!("Try again.");
                continue;
            }
        }
    }; //end loop


    let mut output = String::new();

    for word in input.split_whitespace() {
        let first = &word[0..1];  //Get first char
        let rest = &word[1..]; //Get char 1 to end

        output += rest;
        output += first;
        output += "ay";
        output += " ";   //"ay " + last each word
    }

    print!("{output}");  //display output var 
}

fn get_line() -> Result<String, io::Error> {
    let mut input = String::new();

    io::BufReader::new(io::stdin().take(1862)).read_line(&mut input)?;

    Ok(input)
}
