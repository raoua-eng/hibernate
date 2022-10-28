package filterBug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SqlFragmentAlias;

@Entity(name = "Account")
@Table(name = "account")
@SecondaryTable(name = "account_details")

@FilterDefs({
		@FilterDef(name = "activeAccountV1", parameters = @ParamDef(name = "active", type = "boolean")),
		@FilterDef(name = "activeAccountV2", parameters = @ParamDef(name = "active", type = "boolean"))})
@Filters({
		@Filter(name = "activeAccountV1", condition = "{a}.active = :active and {ad}.deleted = false", aliases = {
				//@SqlFragmentAlias(alias = "a", table = "account"), --NOT OK
				@SqlFragmentAlias(alias = "a", table = "TEST.account"), //OK
				//@SqlFragmentAlias(alias = "ad", table = "TEST.account_details"), --NOT OK
				@SqlFragmentAlias(alias = "ad", table = "TEST.account_details"), //OK 
		}),
		@Filter(name = "activeAccountV2", condition = "{a}.active = :active", aliases = {
				//@SqlFragmentAlias(alias = "a", entity =AccountPO.class ) --NOT OK
				@SqlFragmentAlias(alias = "a", table = "TEST.account") //OK
		})
})
public class AccountPO {

	@Id
	private Long id;

	private Double amount;

	private Double rate;

	private boolean active;

	@Column(table = "account_details")
	private boolean deleted;

	public AccountPO() {
		super();
	}

	public AccountPO(Long id, Double amount, Double rate, boolean active, boolean deleted) {
		super();
		this.id = id;
		this.amount = amount;
		this.rate = rate;
		this.active = active;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isdeleted() {
		return deleted;
	}

	public void setdeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "AccountPO [id=" + id + ", amount=" + amount + ", rate=" + rate + ", active=" + active + ", deleted="
				+ deleted + "]";
	}

}
