package org.shefron.designpattern.behaviour.command_transaction;

public class PasteCommand extends Command {

	private Document doc = null;
	private Object cache = null;

	public PasteCommand(Document doc) {
		this.doc = doc;
	}

	@Override
	protected void execute() {
		cache = doc.getTitle();
		doc.setTitle("123321");
	}

	@Override
	protected void undo() {
		doc.setTitle(cache.toString());
	}

	public Document getDocument() {
		return this.doc;
	}

}
