package org.shefron.designpattern.behaviour.command_transaction;

public class WriteCommand extends Command {

	private Document doc = null;
	private Object obj = null;

	public WriteCommand(Document doc) {
		this.doc = doc;
	}

	@Override
	protected void execute() {
		obj = doc.getAuthor();
		doc.setAuthor("other");
	}

	@Override
	protected void undo() {
		doc.setAuthor(obj.toString());
	}

}
